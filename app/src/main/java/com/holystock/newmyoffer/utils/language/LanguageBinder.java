package com.holystock.newmyoffer.utils.language;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

public final class LanguageBinder {

    private static final Map<View, String> TEXT_MAP = new HashMap<>();
    private static final Map<View, String> HINT_MAP = new HashMap<>();

    private LanguageBinder() {
    }

    /**
     * Bind Activity
     */
    public static void bind(@NonNull Activity activity) {

        if (!(activity instanceof LifecycleOwner))
            return;

        scan(activity.getWindow().getDecorView());

        LanguageManager.getInstance()
                .getLanguageLiveData()
                .observe((LifecycleOwner) activity, lang -> refresh());
    }

    /**
     * Register Text
     */
    public static void text(View view, String key) {

        TEXT_MAP.put(view, key);

        applyText(view, key);
    }

    /**
     * Register Hint
     */
    public static void hint(View view, String key) {

        HINT_MAP.put(view, key);

        applyHint(view, key);
    }

    /**
     * Refresh All
     */
    public static void refresh() {

        for (Map.Entry<View, String> entry : TEXT_MAP.entrySet()) {

            applyText(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<View, String> entry : HINT_MAP.entrySet()) {

            applyHint(entry.getKey(), entry.getValue());
        }
    }

    private static void scan(View view) {

        Object tag = view.getTag();

        if (tag instanceof String) {

            String key = (String) tag;

            if (view instanceof EditText) {

                hint(view, key);

            } else {

                text(view, key);
            }
        }

        if (view instanceof ViewGroup) {

            ViewGroup group = (ViewGroup) view;

            for (int i = 0; i < group.getChildCount(); i++) {

                scan(group.getChildAt(i));
            }
        }
    }

    private static void applyText(View view, String key) {

        String value = LanguageManager.getInstance().getString(key);

        if (view instanceof Button) {

            ((Button) view).setText(value);

        } else if (view instanceof CheckBox) {

            ((CheckBox) view).setText(value);

        } else if (view instanceof RadioButton) {

            ((RadioButton) view).setText(value);

        } else if (view instanceof TextView) {

            ((TextView) view).setText(value);
        }
    }

    private static void applyHint(View view, String key) {

        if (view instanceof EditText) {

            ((EditText) view)
                    .setHint(LanguageManager.getInstance().getString(key));
        }
    }
}
