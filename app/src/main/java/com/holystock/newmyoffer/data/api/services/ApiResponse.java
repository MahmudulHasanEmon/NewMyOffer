package com.holystock.newmyoffer.data.api.services;

import static com.holystock.newmyoffer.utils.Helper.TAG;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * Modern & Enterprise Grade API Response Wrapper
 */
public final class ApiResponse {

    private final boolean isSuccess;
    private final Object data;
    private final String message;
    private final int statusCode;

    // Private Constructor
    private ApiResponse(boolean isSuccess, Object data, String message, int statusCode) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    // =======================
    // Factory Constructors
    // =======================
    public static ApiResponse success(Object data, int statusCode) {
        return new ApiResponse(true, data, "Success", statusCode);
    }

    public static ApiResponse success(Object data, String message, int statusCode) {
        return new ApiResponse(true, data, message, statusCode);
    }

    public static ApiResponse failure(String message, int statusCode) {
        return new ApiResponse(false, null, message, statusCode);
    }

    public static ApiResponse failure(int statusCode) {
        return new ApiResponse(false, null, getMessageForStatusCode(statusCode), statusCode);
    }

    // =======================
    // Getters
    // =======================
    public boolean isSuccess() {
        return isSuccess;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get Root Data as JSONObject safely
     */
    public JSONObject getJson() {
        if (data instanceof JSONObject) {
            return (JSONObject) data;
        }
        return null;
    }

    // =======================
    // ADVANCED NESTED PARSER (Null Safe)
    // =======================

    /**
     * Root JSON অথবা Dot Notation Path ("data.user.name") থেকে ভ্যালু নেওয়ার জন্য
     */
    public Object getNestedValue(String path) {
        JSONObject current = getJson();
        if (current == null || path == null || path.trim().isEmpty()) {
            return null;
        }

        // Path-এ dot না থাকলে এবং Root-এ সরাসরি Key থাকলে আগে সেটা নিয়ে নেবে
        if (!path.contains(".") && current.has(path)) {
            return current.opt(path);
        }

        String[] keys = path.split("\\.");
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].trim();

            if (i == keys.length - 1) {
                return current.opt(key);
            }

            current = current.optJSONObject(key);
            if (current == null) {
                return null; // Safe Exit for Wrong Path
            }
        }

        return null;
    }

    // =======================
    // SMART TYPED GETTERS
    // =======================

    /**
     * String মান পাওয়ার জন্য
     * Single level: getString("message")
     * Nested level: getString("data.user.name")
     */
    public String getString(String path) {
        Object val = getNestedValue(path);
        if (val == null || val == JSONObject.NULL) {
            return "";
        }
        // Array বা Complex Object হলে সরাসরি stringify না করে safe text রিটার্ন করবে
        if (val instanceof JSONObject || val instanceof JSONArray) {
            return val.toString();
        }
        return String.valueOf(val).trim();
    }

    /**
     * Integer মান পাওয়ার জন্য
     */
    public int getInt(String path) {
        Object val = getNestedValue(path);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        if (val != null) {
            try {
                return Integer.parseInt(val.toString().trim());
            } catch (NumberFormatException ignored) {}
        }
        return 0;
    }

    /**
     * Double মান পাওয়ার জন্য
     */
    public double getDouble(String path) {
        Object val = getNestedValue(path);
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        if (val != null) {
            try {
                return Double.parseDouble(val.toString().trim());
            } catch (NumberFormatException ignored) {}
        }
        return 0.0;
    }

    /**
     * Boolean মান পাওয়ার জন্য
     */
    public boolean getBoolean(String path) {
        Object val = getNestedValue(path);
        if (val instanceof Boolean) {
            return (Boolean) val;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue() == 1;
        }
        return val != null && ("true".equalsIgnoreCase(val.toString().trim()) || "1".equals(val.toString().trim()));
    }

    /**
     * JSONObject পাওয়ার জন্য
     */
    public JSONObject getJsonObject(String path) {
        Object val = getNestedValue(path);
        if (val instanceof JSONObject) {
            return (JSONObject) val;
        }
        return null;
    }

    /**
     * JSONArray পাওয়ার জন্য
     */
    public JSONArray getJsonArray(String path) {
        Object val = getNestedValue(path);
        if (val instanceof JSONArray) {
            return (JSONArray) val;
        }
        return null;
    }

    // =======================
    // Status Code Mapping Helper
    // =======================
    private static String getMessageForStatusCode(int statusCode) {
        switch (statusCode) {
            case 400: return "Bad Request";
            case 401: return "Unauthorized access";
            case 403: return "Access Forbidden";
            case 404: return "Requested resource not found";
            case 422: return "Unprocessable Entity";
            case 500: return "Internal Server Error";
            case 502: return "Bad Gateway";
            case 503: return "Service Unavailable";
            case 600: return "No Internet Connection";
            case 601: return "SSL Handshake Failed";
            case 602: return "TLS Connection Error";
            case 603: return "Connection Failed";
            default: return "An unexpected error occurred (Code: " + statusCode + ")";
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "isSuccess=" + isSuccess +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}