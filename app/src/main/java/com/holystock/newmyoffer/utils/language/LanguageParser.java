package com.holystock.newmyoffer.utils.language;

import android.content.Context;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public final class LanguageParser {

    private LanguageParser() {
    }

    public static HashMap<String, String> parse(Context context, String language) {

        HashMap<String, String> map = new HashMap<>();

        try {

            int rawId = context.getResources().getIdentifier(
                    language,
                    "raw",
                    context.getPackageName()
            );

            if (rawId == 0) {
                return map;
            }

            InputStream inputStream =
                    context.getResources().openRawResource(rawId);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");

            JSONObject object = new JSONObject(json);

            Iterator<String> iterator = object.keys();

            while (iterator.hasNext()) {

                String key = iterator.next();

                map.put(key, object.optString(key, key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}