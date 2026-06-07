package com.holystock.newmyoffer.utils.appThemes;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.holystock.newmyoffer.R;

public class Status {

    private final Activity activity;

    public Status(Activity context) {
        this.activity = context;
    }

    public void setLightStatusBar() {

        Window window = activity.getWindow();
        window.setStatusBarColor(activity.getColor(R.color.statusBar)); // Status bar ট্রান্সপারেন্ট করা হলো
        window.setNavigationBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        ViewCompat.setOnApplyWindowInsetsListener(activity.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());

            // Status bar, nav bar এবং কীবোর্ড ওপেন অবস্থায় সঠিক padding
            int bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom);

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottomPadding);

            return insets;
        });
    }

}

