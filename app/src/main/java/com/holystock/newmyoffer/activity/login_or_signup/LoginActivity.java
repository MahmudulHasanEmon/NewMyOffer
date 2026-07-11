package com.holystock.newmyoffer.activity.login_or_signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.utils.Helper;
import com.holystock.newmyoffer.utils.LanguageManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.views.BorderView;
import com.holystock.newmyoffer.views.LanguageToggleButton;

public class LoginActivity extends BaseActivity {

    private EditText edNomber;
    private BorderView nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Status(this).setLightStatusBar();

        TextView tvTerms = findViewById(R.id.tvTerms);
        tvTerms.setText(Html.fromHtml("<u>নিয়ম ও শর্তসমূহ</u>", Html.FROM_HTML_MODE_LEGACY));

        //findViewById(R.id.nextBtn).setOnClickListener(v -> startActivity(new Intent(this, OtpActivity.class)));
        findViewById(R.id.nextBtn).setOnClickListener(v -> openActivity(OtpActivity.class,null));

        LanguageToggleButton toggle = findViewById(R.id.languageToggle);
        toggle.init(this);

        LanguageManager.get().bind(this, findViewById(R.id.tvTitle), "login_or_register");

        edNomber = findViewById(R.id.edNomber);
        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setBackgroundColorCustom(getColor(R.color.unselected));
        nextBtn.setClickable(false);


        edNomber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Helper.isBangladeshiMobile(s.toString())){
                    nextBtn.setBackgroundColorCustom(getColor(R.color.selectedDark));
                    nextBtn.setClickable(true);
                }else{
                    nextBtn.setBackgroundColorCustom(getColor(R.color.unselected));
                    nextBtn.setClickable(false);
                }

            }
        });



    }
}