package com.holystock.newmyoffer.data.api;

public final class ApiConfig {

    // Private constructor to prevent instantiation
    private ApiConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // =======================
    // Base Config
    // =======================
    public static final String BASE_URL = Config.BASE_URL;
    public static final String API_VERSION = "/api/user";
    public static final String API_URL = BASE_URL + API_VERSION;

    // =======================
    // Auth User
    // =======================
    public static final String SEND_OTP = "/send-otp";
    public static final String VERIFY_OTP = "/verify-otp";
    public static final String LOGIN = "/login";
    public static final String PIN_LOGIN = "/pin-login";
    public static final String REFRESH_PIN_LOGIN = "/refresh-token";
    public static final String REGISTER = "/register";

    // =======================
    // Auth User Actions
    // =======================
    public static final String LOGOUT = "/logout";
    public static final String PROFILE = "/profile";
    public static final String BALANCE = "/balance";
    public static final String BKASH_PAYMENT = "/bkash-payment";

    // =======================
    // Users Management
    // =======================
    public static final String USERS = "/users";
    public static final String USER_ROLES_PERMISSIONS = "/users-roles-permissions";

    // =======================
    // Offers and Recharge Management
    // =======================
    public static final String RECHARGE_INFO = "/recharge-info";
    public static final String RECHARGE = "/recharge";

    // =======================
    // Add Money Management
    // =======================
    public static final String ADD_MONEY_INFO = "/payment-info";

    // =======================
    // Transactions Management
    // =======================
    public static final String TRANSACTIONS = "/transactions";
    public static final String LAST_FIVE_TRANSACTIONS = "/transactions/last-five";
}
