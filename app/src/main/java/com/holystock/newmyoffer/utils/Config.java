package com.holystock.newmyoffer.utils;

public final class Config {

    // Private constructor to prevent instantiation
    private Config() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // =======================
    // Base Config
    // =======================
    public static final String BASE_URL = "http://192.168.0.235:8000";
    // public static final String BASE_URL = "https://myoffer.mahmudulhasanemon.com";

    // SAME key as Java (16 bytes)
    public static final String ENCRYPTER_KEY = "1234567890123456";
}
