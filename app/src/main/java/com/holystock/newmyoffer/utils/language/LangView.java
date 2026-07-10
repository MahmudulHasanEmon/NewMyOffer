package com.holystock.newmyoffer.utils.language;

import android.view.View;

import androidx.annotation.NonNull;

public class LangView {

    private final View view;
    private final String key;
    private final boolean hint;

    public LangView(@NonNull View view,
                    @NonNull String key,
                    boolean hint) {

        this.view = view;
        this.key = key;
        this.hint = hint;
    }

    public View getView() {
        return view;
    }

    public String getKey() {
        return key;
    }

    public boolean isHint() {
        return hint;
    }
}
