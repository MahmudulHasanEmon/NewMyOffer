package com.holystock.newmyoffer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LanguageManager {

    private static LanguageManager instance;

    private final MutableLiveData<String> languageLive = new MutableLiveData<>();

    private final Map<String, String> strings = new HashMap<>();

    private Context context;

    private LanguageManager() {}

    public static LanguageManager get() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void bind(LifecycleOwner owner, TextView view, String key) {
        view.setText(getString(key));
        observe().observe(owner, lang -> view.setText(getString(key)));
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

        SharedPreferences pref =
                this.context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        String lang = pref.getString("language", "en");

        load(lang);
    }

    public LiveData<String> observe() {
        return languageLive;
    }

    public void setLanguage(String lang) {

        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .edit()
                .putString("language", lang)
                .apply();

        load(lang);
    }

    private void load(String lang) {

        try {

            int id = context.getResources().getIdentifier(
                    lang,
                    "raw",
                    context.getPackageName());

            String json =
                    new java.util.Scanner(
                            context.getResources().openRawResource(id))
                            .useDelimiter("\\A")
                            .next();
            
            JSONObject object = new JSONObject(json);

            strings.clear();

            Iterator<String> keys = object.keys();

            while (keys.hasNext()) {

                String key = keys.next();

                strings.put(key, object.getString(key));
            }

            languageLive.postValue(lang);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {

        String value = strings.get(key);

        return value == null ? key : value;
    }

}
