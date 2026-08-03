package com.holystock.newmyoffer.data.api.services;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.holystock.newmyoffer.data.api.ApiConfig;
import com.holystock.newmyoffer.utils.helpers.AppPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class ApiService {

    private static final String TAG = "ApiService";
    private static final String CONTENT_TYPE = "application/json";
    private static final String ACCEPT = "application/json";
    private static final int TIMEOUT_MS = 60000; // 60 seconds

    // Enum representing HTTP Methods
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

        String id = (deviceID != null) ? deviceID : AppPreferences.getString("device_id", null);
        String pin = (userPIN != null) ? userPIN : AppPreferences.getString("device_id", null);
        //String userAgent = DeviceHelper.getUserAgent();

        headers.put("Content-Type", CONTENT_TYPE);
        headers.put("Accept", ACCEPT);
        //headers.put("User-Agent", userAgent);

        if (pin != null) headers.put("X-User-Pin", pin);
        if (id != null) headers.put("X-Device-Id", id);
        if (token != null) headers.put("Authorization", "Bearer " + token);

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
            // 1. Build URI with Query Parameters
            Uri.Builder uriBuilder = Uri.parse(ApiConfig.API_URL + endpoint).buildUpon();
            if (parameters != null) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            String url = uriBuilder.build().toString();
            Log.d(TAG, "uri: " + url);

            String deviceID = AppPreferences.getString("device_id", null);

            // 2. Prepare JSON Body Payload
            JSONObject jsonBody = (body != null) ? new JSONObject(body) : null;

            // 3. Create Volley Request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    method.getVolleyMethod(),
                    url,
                    jsonBody,
                    response -> {
                        future.complete(handleResponse(response));
                    },
                    error -> {

                        future.complete(handleVolleyError(error));
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ApiService.getHeaders(token, deviceID, null);
                }
            };

            // Set Timeout and Retry Policy (maxRetries = 2)
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    2, // Max retries
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            // Add request to Volley Queue
            VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e) {
            future.complete(ApiResponse.failure(StatusCode.CONNECTION_FAILED));
        }

        return future;
    }

    // =======================
    // RESPONSE HANDLER
    // =======================
    private static ApiResponse handleResponse(JSONObject response) {
        try {
            int statusCode = response.optInt("status_code", 200);
            String message = response.optString("message", "");

            Log.d(TAG, "statusCode " + statusCode);
            Log.d(TAG, "message " + message);

            if (statusCode >= 200 && statusCode < 300) {
                return ApiResponse.success(response, statusCode);
            } else {
                String errorMsg = !message.isEmpty() ? message : "Something went wrong";
                return ApiResponse.failure(errorMsg, statusCode);
            }
        } catch (Exception e) {
            return ApiResponse.failure(StatusCode.CONNECTION_FAILED);
        }
    }

    // =======================
    // VOLLEY ERROR HANDLER
    // =======================
    private static ApiResponse handleVolleyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return ApiResponse.failure(StatusCode.NO_INTERNET);
        } else if (error instanceof ServerError) {
            return ApiResponse.failure(StatusCode.INTERNAL_SERVER_ERROR);
        } else if (error instanceof AuthFailureError) {
            return ApiResponse.failure("Authentication Failed", 401);
        } else if (error instanceof ParseError) {
            return ApiResponse.failure(StatusCode.UNPROCESSABLE_ENTITY);
        } else if (error instanceof NetworkError) {
            return ApiResponse.failure(StatusCode.CONNECTION_FAILED);
        } else {
            return ApiResponse.failure(StatusCode.CONNECTION_FAILED);
        }
    }

    // =======================
    // SHORTCUT METHODS
    // =======================
    public static CompletableFuture<ApiResponse> get(
            String endpoint,
            String token,
            Map<String, Object> body,
            Map<String, String> parameters
    ) {
        return request(HttpMethod.GET, endpoint, body, parameters, token);
    }

    public static CompletableFuture<ApiResponse> post(
            String endpoint,
            Map<String, Object> body,
            String token,
            Map<String, String> parameters
    ) {
        return request(HttpMethod.POST, endpoint, body, parameters, token);
    }

    public static CompletableFuture<ApiResponse> put(
            String endpoint,
            Map<String, Object> body,
            String token
    ) {
        return request(HttpMethod.PUT, endpoint, body, null, token);
    }

    public static CompletableFuture<ApiResponse> delete(
            String endpoint,
            Map<String, Object> body,
            String token
    ) {
        return request(HttpMethod.DELETE, endpoint, body, null, token);
    }
}
