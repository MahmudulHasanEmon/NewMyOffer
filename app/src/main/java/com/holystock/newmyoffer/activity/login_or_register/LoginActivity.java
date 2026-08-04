package com.holystock.newmyoffer.activity.login_or_register;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.data.api.ApiConfig;
import com.holystock.newmyoffer.data.api.services.ApiService;
import com.holystock.newmyoffer.utils.Helper;
import com.holystock.newmyoffer.utils.LanguageManager;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.dialog.LoadingDialog;
import com.holystock.newmyoffer.views.BorderView;
import com.holystock.newmyoffer.views.LanguageToggleButton;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private EditText edNumber;
    private BorderView nextBtn;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Status(this).setLightStatusBar();
        dialog = new LoadingDialog(this);

        TextView tvTerms = findViewById(R.id.tvTerms);
        tvTerms.setText(Html.fromHtml("<u>নিয়ম ও শর্তসমূহ</u>", Html.FROM_HTML_MODE_LEGACY));

        //findViewById(R.id.nextBtn).setOnClickListener(v -> startActivity(new Intent(this, OtpActivity.class)));
        //findViewById(R.id.nextBtn).setOnClickListener(v -> openActivity(OtpActivity.class,null));

        LanguageToggleButton toggle = findViewById(R.id.languageToggle);
        toggle.init(this);

        LanguageManager.get().bind(this, findViewById(R.id.tvTitle), "login_or_register");

        edNumber = findViewById(R.id.edNomber);
        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setBackgroundColorCustom(getColor(R.color.unselected));
        nextBtn.setClickable(false);

        edNumber.addTextChangedListener(new TextWatcher() {
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


        findViewById(R.id.nextBtn).setOnClickListener(v -> {

            dialog.start();
            Map<String, Object> body = new HashMap<>();
            body.put("phone", "88"+edNumber.getText().toString());

            ApiService.post(ApiConfig.SEND_OTP, body, null, null)
                    .thenAccept(apiResponse -> {
                        if (apiResponse.isSuccess()) {

                            Bundle bundle = new Bundle();
                            bundle.putString("phone", "88"+edNumber.getText().toString());
                            bundle.putString("otp_preview", apiResponse.getNestedValue("data.otp_preview").toString());
                            bundle.putString("session_token", apiResponse.getNestedValue("data.session_token").toString());

                            openActivity(OtpActivity.class, bundle);
                            Log.d(Helper.TAG, "Success: " + apiResponse.getJson().toString());
                            Log.d(Helper.TAG, "Success: " + apiResponse.getNestedValue("data.otp_preview").toString());
                            Log.d(Helper.TAG, "Success: " + apiResponse.getNestedValue("data.session_token").toString());

                        } else {
                            Log.e(Helper.TAG, "Failed: " + apiResponse.getMessage());
                        }

                        dialog.dismiss();
                    });


        });




    }
}