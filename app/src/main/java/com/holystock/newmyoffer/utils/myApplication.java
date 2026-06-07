package com.holystock.newmyoffer.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Locale;

public class myApplication extends Application implements Application.ActivityLifecycleCallbacks{

    private int activityReferences = 0;
    private long lastBackgroundTime = 0;
    private static final long TIMEOUT = 60_000; // 1 মিনিট

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activityReferences == 0 && System.currentTimeMillis() - lastBackgroundTime > TIMEOUT) {
            // অ্যাপ ব্যাকগ্রাউন্ড থেকে ফিরেছে এবং সময় পার হয়ে গেছে
            if (!(activity instanceof com.holystock.newmyoffer.activity.WelcomeActivity)) {
                Intent intent = new Intent(activity, com.holystock.newmyoffer.activity.login_or_signup.LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
            }
        }
        activityReferences++;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        activityReferences--;
        boolean isActivityChangingConfigurations = false;
        if (activityReferences == 0 && !isActivityChangingConfigurations) {
            lastBackgroundTime = System.currentTimeMillis();
        }
    }

    @Override public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {}
    @Override public void onActivityStarted(@NonNull Activity activity) {}
    @Override public void onActivityStopped(@NonNull Activity activity) {}
    @Override public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}
    @Override public void onActivityDestroyed(@NonNull Activity activity) {}

    @Override
    public void onCreate() {
        super.onCreate();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        registerActivityLifecycleCallbacks(this);
    }

}
