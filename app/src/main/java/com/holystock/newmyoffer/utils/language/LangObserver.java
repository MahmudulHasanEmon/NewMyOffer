package com.holystock.newmyoffer.utils.language;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class LangObserver {

    public interface OnLanguageChanged {
        void onChanged(String language);
    }

    private final Observer<String> observer;

    public LangObserver(@NonNull LifecycleOwner owner,
                        @NonNull OnLanguageChanged callback) {

        observer = callback::onChanged;

        LanguageManager.getInstance()
                .getLanguageLiveData()
                .observe(owner, observer);
    }

    public void remove(@NonNull LifecycleOwner owner) {

        LanguageManager.getInstance()
                .getLanguageLiveData()
                .removeObservers(owner);
    }
}
