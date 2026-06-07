package com.holystock.newmyoffer.activity;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(adjustFontScale(newBase));
    }

    // 🔹 ডিভাইস ফন্ট সাইজ ঠিক রাখে (১.০ মানে ডিফল্ট সাইজ)
    private Context adjustFontScale(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.fontScale = 1.0f;
        return context.createConfigurationContext(configuration);
    }
}
