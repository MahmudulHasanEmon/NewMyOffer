package com.holystock.newmyoffer.data.api.services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.holystock.newmyoffer.data.api.ApiConfig;
import com.holystock.newmyoffer.utils.helpers.AppPreferences;
import com.holystock.newmyoffer.utils.helpers.DeviceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Enterprise Grade Safe Volley Networking Service
 */
public final class ApiService {

    private static final String TAG = "ApiService";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String ACCEPT = "application/json";
    private static final int TIMEOUT_MS = 30000; // 30 Seconds

    public enum HttpMethod {
        GET(Request.Method.GET),
        POST(Request.Method.POST),
        PUT(Request.Method.PUT),
        DELETE(Request.Method.DELETE);

        private final int volleyMethod;

        HttpMethod(int volleyMethod) {
            this.volleyMethod = volleyMethod;
        }

        public int getVolleyMethod() {
            return volleyMethod;
        }
    }

    private ApiService() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // =======================
    // HEADERS
    // =======================
    public static Map<String, String> getHeaders(String token, String deviceID, String userPIN) {
        Map<String, String> headers = new HashMap<>();
        Context context = VolleySingleton.getInstance().getContext();

        String id = (deviceID != null) ? deviceID : DeviceUtils.getDeviceId(context);
        String pin = (userPIN != null) ? userPIN : AppPreferences.getString("pin", null);

        String userAgent = (context != null) ? DeviceUtils.getUserAgent(context) : "AndroidApp/1.0";

        headers.put("Content-Type", CONTENT_TYPE);
        headers.put("Accept", ACCEPT);
        headers.put("User-Agent", userAgent);

        if (pin != null && !pin.isEmpty()) {
            headers.put("X-User-Pin", pin);
        }
        headers.put("X-Device-Id", id);

        if (token != null && !token.trim().isEmpty()) {
            headers.put("Authorization", "Bearer " + token);
        }

        return headers;
    }

    // =======================
    // GENERIC REQUEST
    // =======================
    public static CompletableFuture<ApiResponse> request(
            HttpMethod method,
            String endpoint,
            Map<String, Object> body,
            Map<String, String> parameters,
            String token
    ) {
        CompletableFuture<ApiResponse> future = new CompletableFuture<>();

        try {
            Uri.Builder uriBuilder = Uri.parse(ApiConfig.API_URL + endpoint).buildUpon();
            if (parameters != null) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
                    }
                }
            }
            String url = uriBuilder.build().toString();
            Log.d(TAG, "Request [" + method.name() + "]: " + url);

            String deviceID = AppPreferences.getString("device_id", null);
            JSONObject jsonBody = mapToJsonObject(body);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    method.getVolleyMethod(),
                    url,
                    jsonBody,
                    response -> {
                        Log.d(TAG, "Response Success: " + response.toString());
                        future.complete(handleResponse(response));
                    },
                    error -> future.complete(handleVolleyError(error))
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ApiService.getHeaders(token, deviceID, null);
                }

                @Override
                public String getBodyContentType() {
                    return CONTENT_TYPE;
                }
            };

            // FIX: Set maxRetries to 0 so Volley doesn't duplicate OTP or Payment requests
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    0, // 0 retries
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e) {
            Log.e(TAG, "Exception during request setup", e);
            future.complete(ApiResponse.failure("Connection Setup Failed", 603));
        }

        return future;
    }

    // =======================
    // SAFE MAP TO JSON CONVERTER
    // =======================
    /**
     * Converts Map<String, Object> to JSONObject safely supporting nested Maps & Collections
     */
    @SuppressWarnings("unchecked")
    private static JSONObject mapToJsonObject(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (key == null) continue;

                if (value == null) {
                    jsonObject.put(key, JSONObject.NULL);
                } else if (value instanceof Map) {
                    jsonObject.put(key, mapToJsonObject((Map<String, Object>) value));
                } else if (value instanceof Collection) {
                    jsonObject.put(key, new JSONArray((Collection<?>) value));
                } else {
                    jsonObject.put(key, value);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error building JSON Object from Map", e);
        }
        return jsonObject;
    }

    // =======================
    // RESPONSE HANDLER
    // =======================
    private static ApiResponse handleResponse(JSONObject response) {
        if (response == null) {
            return ApiResponse.failure("Null Server Response", 603);
        }

        int statusCode = response.optInt("status_code", 200);
        String message = response.optString("message", "");

        Log.d(TAG, "Parsed Response - Code: " + statusCode + ", Message: " + message);

        // 1. Success Response (200 - 299)
        if (statusCode >= 200 && statusCode < 300) {
            if (!message.isEmpty()) {
                return ApiResponse.success(response, message, statusCode);
            } else {
                return ApiResponse.success(response, statusCode);
            }
        }
        // 2. Error Response (400, 401, 422, 500, etc.)
        else {
            String errorMsg = !message.isEmpty() ? message : "Error occurred (Code: " + statusCode + ")";
            return ApiResponse.failure(errorMsg, statusCode);
        }
    }

    // =======================
    // ENHANCED VOLLEY ERROR HANDLER
    // =======================
    private static ApiResponse handleVolleyError(VolleyError error) {
        NetworkResponse response = error.networkResponse;

        if (response != null) {
            int httpStatusCode = response.statusCode;
            try {
                String jsonString = new String(response.data, StandardCharsets.UTF_8);
                Log.e(TAG, "Server Error Response [" + httpStatusCode + "]: " + jsonString);

                JSONObject jsonError = new JSONObject(jsonString);
                String message = jsonError.optString("message", "HTTP Error " + httpStatusCode);
                return ApiResponse.failure(message, httpStatusCode);
            } catch (Exception e) {
                Log.e(TAG, "Failed to parse error response body", e);
                return ApiResponse.failure("HTTP Error " + httpStatusCode, httpStatusCode);
            }
        }

        Log.e(TAG, "Network Level Error: " + error.toString(), error);

        if (error instanceof TimeoutError) {
            return ApiResponse.failure("Request Timeout. Please try again.", 600);
        } else if (error instanceof NoConnectionError) {
            return ApiResponse.failure("No internet connection available.", 600);
        } else if (error instanceof AuthFailureError) {
            return ApiResponse.failure("Unauthorized access.", 401);
        } else if (error instanceof ParseError) {
            return ApiResponse.failure("Invalid server response format.", 422);
        } else {
            return ApiResponse.failure("Network connection failed.", 603);
        }
    }

    // =======================
    // SHORTCUT METHODS
    // =======================
    public static CompletableFuture<ApiResponse> get(String endpoint, String token, Map<String, Object> body, Map<String, String> parameters) {
        return request(HttpMethod.GET, endpoint, body, parameters, token);
    }

    public static CompletableFuture<ApiResponse> post(String endpoint, Map<String, Object> body, String token, Map<String, String> parameters) {
        return request(HttpMethod.POST, endpoint, body, parameters, token);
    }

    public static CompletableFuture<ApiResponse> put(String endpoint, Map<String, Object> body, String token) {
        return request(HttpMethod.PUT, endpoint, body, null, token);
    }

    public static CompletableFuture<ApiResponse> delete(String endpoint, Map<String, Object> body, String token) {
        return request(HttpMethod.DELETE, endpoint, body, null, token);
    }
}