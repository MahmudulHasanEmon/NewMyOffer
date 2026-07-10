package com.holystock.newmyoffer.utils.language;

import android.content.Context;
import android.content.SharedPreferences;

public final class LanguageStorage {

    private static final String PREF_NAME = "settings";
    private static final String KEY_LANGUAGE = "language";

    private LanguageStorage() {
        // Prevent instantiation
    }

    public static void save(Context context, String language) {
        SharedPreferences preferences =
                context.getApplicationContext()
                        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        preferences.edit()
                .putString(KEY_LANGUAGE, language)
                .apply();
    }

    public static String get(Context context) {
        SharedPreferences preferences =
                context.getApplicationContext()
                        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return preferences.getString(KEY_LANGUAGE, "en");
    }

    public static boolean isBangla(Context context) {
        return "bn".equals(get(context));
    }

    public static boolean isEnglish(Context context) {
        return "en".equals(get(context));
    }

    public static void clear(Context context) {
        SharedPreferences preferences =
                context.getApplicationContext()
                        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        preferences.edit()
                .remove(KEY_LANGUAGE)
                .apply();
    }
}
