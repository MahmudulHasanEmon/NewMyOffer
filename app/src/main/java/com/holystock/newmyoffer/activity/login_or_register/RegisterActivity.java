package com.holystock.newmyoffer.activity.login_or_register;

import android.os.Bundle;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.utils.appThemes.Status;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new Status(this).setLightStatusBar();






    }
}