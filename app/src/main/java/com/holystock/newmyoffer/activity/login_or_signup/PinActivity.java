package com.holystock.newmyoffer.activity.login_or_signup;

import android.content.Intent;
import android.os.Bundle;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.activity.home.HomeActivity;
import com.holystock.newmyoffer.controllers.KeyboardController;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.views.BottomNextButtonView;

public class PinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        new Status(this).setLightStatusBar();
        BottomNextButtonView buttonView = findViewById(R.id.bottomNextButton);


        new KeyboardController(
                this,
                findViewById(R.id.keyboardRootLayout),
                findViewById(R.id.tvDisplay),
                value -> {
                    buttonView.setButtonClickable(value.length() == 5);
                }, null
        ).showKeyboard();

        buttonView.setOnClickListener(v -> startActivity(new Intent(PinActivity.this, HomeActivity.class)));


    }
}