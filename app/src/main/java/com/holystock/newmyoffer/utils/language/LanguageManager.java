package com.holystock.newmyoffer.utils.language;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class LanguageManager {

    private static final String PREF_NAME = "settings";
    private static final String KEY_LANGUAGE = "language";

    private static LanguageManager instance;

    private Context context;

    private final MutableLiveData<String> languageLiveData = new MutableLiveData<>();

    private final HashMap<String, String> translations = new HashMap<>();

    private LanguageManager() {
    }

    public static synchronized LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    /**
     * Call once from Application.onCreate()
     */
    public void init(Context context) {
        this.context = context.getApplicationContext();

        String lang = getSavedLanguage();

        loadLanguage(lang);
    }

    public LiveData<String> getLanguageLiveData() {
        return languageLiveData;
    }

    public String getCurrentLanguage() {
        return getSavedLanguage();
    }

    public void setLanguage(String language) {

        if (language == null) return;

        saveLanguage(language);

        loadLanguage(language);

        languageLiveData.postValue(language);
    }

    public String getString(String key) {

        if (translations.containsKey(key)) {
            return translations.get(key);
        }

        return key;
    }

    private void loadLanguage(String language) {

        translations.clear();

        translations.putAll(
                LanguageParser.parse(context, language)
        );
    }

   /* private void loadLanguage(String language) {

        try {

            translations.clear();

            int rawId = context.getResources().getIdentifier(
                    language,
                    "raw",
                    context.getPackageName());

            InputStream inputStream =
                    context.getResources().openRawResource(rawId);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer);

            JSONObject object = new JSONObject(json);

            Iterator<String> iterator = object.keys();

            while (iterator.hasNext()) {

                String key = iterator.next();

                translations.put(
                        key,
                        object.optString(key, key)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    private void saveLanguage(String language) {

        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        preferences.edit()
                .putString(KEY_LANGUAGE, language)
                .apply();
    }

    private String getSavedLanguage() {

        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return preferences.getString(KEY_LANGUAGE, "en");
    }

}
