package com.holystock.newmyoffer.activity.login_or_signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.holystock.newmyoffer.MainActivity;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.activity.home.HomeActivity;
import com.holystock.newmyoffer.utils.appThemes.Status;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Status(this).setLightStatusBar();

        TextView tvTerms = findViewById(R.id.tvTerms);
        tvTerms.setText(Html.fromHtml("<u>নিয়ম ও শর্তসমূহ</u>", Html.FROM_HTML_MODE_LEGACY));

        findViewById(R.id.nextBtn).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));


    }
}