package com.holystock.newmyoffer.utils.appThemes;

import android.app.Activity;

import androidx.activity.ComponentActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.holystock.newmyoffer.R;

public class AppToolbarManager {

    private final Activity activity;

    public AppToolbarManager(Activity activity) {
        this.activity = activity;
    }

    private MaterialToolbar toolbar;


    public void init() {

        this.toolbar =
                this.activity.findViewById(R.id.toolbar);

        if (this.toolbar == null) return;

        this.toolbar.setNavigationOnClickListener(v -> {

            if (this.activity instanceof ComponentActivity) {
                ((ComponentActivity) this.activity)
                        .getOnBackPressedDispatcher()
                        .onBackPressed();
            } else {
                this.activity.finish();
            }
        });
    }

    public void setTitle(String title){
        this.toolbar.setTitle(title);
    }

    public MaterialToolbar getTollbar(){
        return this.toolbar;
    }

}