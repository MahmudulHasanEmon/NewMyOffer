package com.holystock.newmyoffer.data.api.services;

public final class StatusCode {

    // Private constructor to prevent instantiation
    private StatusCode() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // =======================
    // Network / Device Errors
    // =======================
    public static final int NO_INTERNET = -1;
    public static final int CONNECTION_FAILED = -2;
    public static final int DNS_ERROR = -3;

    // =======================
    // Custom App-Specific
    // =======================
    public static final int USER_BLOCKED = 101;
    public static final int PAYMENT_PENDING = 102;
    public static final int NOT_VERIFIED = 103;
    public static final int SESSION_EXPIRED = 104;
    public static final int OTP_INVALID = 105;
    public static final int PROFILE_INCOMPLETE = 106;
    public static final int SUBSCRIPTION_EXPIRED = 107;
    public static final int FEATURE_DISABLED = 108;
    public static final int INVALID_PIN = 109;

    // =======================
    // CRUD Operations
    // =======================
    public static final int CREATE_SUCCESS = 110;
    public static final int CREATE_FAILED = 111;
    public static final int READ_SUCCESS = 112;
    public static final int READ_FAILED = 113;
    public static final int UPDATE_SUCCESS = 114;
    public static final int UPDATE_FAILED = 115;
    public static final int DELETE_SUCCESS = 116;
    public static final int DELETE_FAILED = 117;

    // =======================
    // HTTP Success
    // =======================
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;

    // =======================
    // Client Errors
    // =======================
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int CONFLICT = 409;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int TOO_MANY_REQUESTS = 429;
    public static final int SSL_HANDSHAKE_FAILED = 495;
    public static final int TLS_ERROR = 496;
    public static final int CLIENT_CLOSED_REQUEST = 499;

    // =======================
    // Server Errors
    // =======================
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    // =======================
    // Message Getter
    // =======================
    public static String message(int code) {
        switch (code) {

            // Network / Device Errors
            case NO_INTERNET:
                return "No internet connection";
            case CONNECTION_FAILED:
                return "Connection failed";
            case DNS_ERROR:
                return "DNS resolution error";

            // Success
            case OK:
                return "Success";
            case CREATED:
                return "Resource created successfully";
            case ACCEPTED:
                return "Request accepted";
            case NO_CONTENT:
                return "No content";

            // Client Errors
            case BAD_REQUEST:
                return "Bad request";
            case UNAUTHORIZED:
                return "Unauthorized access";
            case FORBIDDEN:
                return "Access forbidden";
            case NOT_FOUND:
                return "Resource not found";
            case METHOD_NOT_ALLOWED:
                return "Method not allowed";
            case REQUEST_TIMEOUT:
                return "Request timeout";
            case CONFLICT:
                return "Data conflict";
            case UNPROCESSABLE_ENTITY:
                return "Unprocessable entity";
            case TOO_MANY_REQUESTS:
                return "Too many requests";
            case SSL_HANDSHAKE_FAILED:
                return "SSL handshake failed";
            case TLS_ERROR:
                return "TLS protocol error";
            case CLIENT_CLOSED_REQUEST:
                return "Client closed request";

            // Server Errors
            case INTERNAL_SERVER_ERROR:
                return "Internal server error";
            case NOT_IMPLEMENTED:
                return "Feature not implemented";
            case BAD_GATEWAY:
                return "Bad gateway";
            case SERVICE_UNAVAILABLE:
                return "Service unavailable";
            case GATEWAY_TIMEOUT:
                return "Gateway timeout";

            // Custom App-Specific
            case USER_BLOCKED:
                return "User is blocked";
            case PAYMENT_PENDING:
                return "Payment pending";
            case NOT_VERIFIED:
                return "Account not verified";
            case SESSION_EXPIRED:
                return "Session expired";
            case OTP_INVALID:
                return "Invalid OTP";
            case PROFILE_INCOMPLETE:
                return "Profile incomplete";
            case SUBSCRIPTION_EXPIRED:
                return "Subscription expired";
            case FEATURE_DISABLED:
                return "Feature disabled";
            case INVALID_PIN:
                return "Invalid PIN";

            // CRUD
            case CREATE_SUCCESS:
                return "Item created successfully";
            case CREATE_FAILED:
                return "Failed to create item";
            case READ_SUCCESS:
                return "Item retrieved successfully";
            case READ_FAILED:
                return "Failed to retrieve item";
            case UPDATE_SUCCESS:
                return "Item updated successfully";
            case UPDATE_FAILED:
                return "Failed to update item";
            case DELETE_SUCCESS:
                return "Item deleted successfully";
            case DELETE_FAILED:
                return "Failed to delete item";

            default:
                return "Unknown status error (" + code + ")";
        }
    }
}
