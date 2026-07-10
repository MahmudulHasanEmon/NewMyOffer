package com.holystock.newmyoffer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.holystock.newmyoffer.R;

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

    protected void openActivity(Class<?> cls, Bundle bundle) {

        Intent intent = new Intent(this, cls);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);

        overridePendingTransition(
                R.anim.activity_open_enter,
                R.anim.activity_open_exit
        );
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(
                R.anim.activity_close_enter,
                R.anim.activity_close_exit
        );
    }
}
