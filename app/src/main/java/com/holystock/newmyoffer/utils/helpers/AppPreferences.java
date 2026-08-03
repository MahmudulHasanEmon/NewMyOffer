package com.holystock.newmyoffer.utils.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppPreferences {
    private static final String PREF_NAME = "app_settings";
    private static SharedPreferences sharedPreferences;

    private AppPreferences() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Initializes the SharedPreferences instance.
     * Call this in Application onCreate() or MainActivity onCreate().
     */
    public static synchronized void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getApplicationContext()
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    private static SharedPreferences getPrefs() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("AppPreferences is not initialized. Call AppPreferences.init(context) first.");
        }
        return sharedPreferences;
    }

    // =======================
    // STRING OPERATIONS
    // =======================
    public static void saveString(String key, String value) {
        getPrefs().edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        return getPrefs().getString(key, defaultValue);
    }

    // =======================
    // BOOLEAN OPERATIONS
    // =======================
    public static void saveBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPrefs().getBoolean(key, defaultValue);
    }

    // =======================
    // INTEGER OPERATIONS
    // =======================
    public static void saveInt(String key, int value) {
        getPrefs().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return getPrefs().getInt(key, defaultValue);
    }

    // =======================
    // DELETE & CLEAR
    // =======================
    public static void removeKey(String key) {
        getPrefs().edit().remove(key).apply();
    }

    public static void clearAll() {
        getPrefs().edit().clear().apply();
    }
}
