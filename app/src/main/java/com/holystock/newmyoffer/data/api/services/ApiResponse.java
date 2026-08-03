package com.holystock.newmyoffer.data.api.services;

import org.json.JSONObject;

public class ApiResponse {

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

    /**
     * Creates a Successful API Response with data and status code
     */
    public static ApiResponse success(Object data, int statusCode) {
        return new ApiResponse(true, data, "Success", statusCode);
    }

    /**
     * Creates a Successful API Response with custom message
     */
    public static ApiResponse success(Object data, String message, int statusCode) {
        return new ApiResponse(true, data, message, statusCode);
    }

    /**
     * Creates a Failure API Response using custom message and status code
     */
    public static ApiResponse failure(String message, int statusCode) {
        return new ApiResponse(false, null, message, statusCode);
    }

    /**
     * Creates a Failure API Response using status code alone
     */
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

    /**
     * Helper to get data as a JSONObject if applicable
     */
    public JSONObject getDataAsJson() {
        if (data instanceof JSONObject) {
            return (JSONObject) data;
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    // =======================
    // Status Code Mapping Helper
    // =======================
    private static String getMessageForStatusCode(int statusCode) {
        switch (statusCode) {
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized access";
            case 403:
                return "Access Forbidden";
            case 404:
                return "Requested resource not found";
            case 422:
                return "Unprocessable Entity";
            case 500:
                return "Internal Server Error";
            case 502:
                return "Bad Gateway";
            case 503:
                return "Service Unavailable";
            case 600: // Custom HttpStatusCode mapping
                return "No Internet Connection";
            case 601:
                return "SSL Handshake Failed";
            case 602:
                return "TLS Connection Error";
            case 603:
                return "Connection Failed";
            default:
                return "An unexpected error occurred (Code: " + statusCode + ")";
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
