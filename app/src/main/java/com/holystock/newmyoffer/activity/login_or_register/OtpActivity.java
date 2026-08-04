package com.holystock.newmyoffer.activity.login_or_register;

import static com.holystock.newmyoffer.utils.Helper.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.data.api.ApiConfig;
import com.holystock.newmyoffer.data.api.services.ApiService;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.dialog.LoadingDialog;
import com.holystock.newmyoffer.views.BorderView;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends BaseActivity {

    private static LoadingDialog dialog;
    private TextView tvNumber;
    private BorderView nextBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        new Status(this).setLightStatusBar();
        dialog = new LoadingDialog(this);
        findViewById(R.id.back).setOnClickListener(v -> finish());

        nextBtn = findViewById(R.id.nextBtn);
        tvNumber = findViewById(R.id.tvNumber);

        // findViewById(R.id.nextBtn).setOnClickListener(v -> openActivity(PinActivity.class, null));

        // 1. Intent Extras নিরাপদে গ্রহণ করুন (Null Check সহ)
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            Toast.makeText(this, "ইনভ্যালিড রিকোয়েস্ট!", Toast.LENGTH_SHORT).show();
            finish(); // ডাটা না থাকলে স্ক্রিন বন্ধ করে দিন
            return;
        }

        String phone = extras.getString("phone", "");
        String otpPreview = extras.getString("otp_preview", "");
        String sessionToken = extras.getString("session_token", "");

        tvNumber.setText("+" + phone);

        PinView pinView = findViewById(R.id.pinView);

        // ১. অটো কীবোর্ড শো ও ফোকাস করা
        pinView.requestFocus();

        // ২. টাইপিং ট্র্যাকিং ও অটো সাবমিট (৬ ডিজিট পূর্ণ হলে)
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    String otp = s.toString();
                    // ৬ ডিজিট ইনপুট হয়ে গেলে সরাসরি API কল করতে পারেন
                    nextBtn.setBackgroundColorCustom(getColor(R.color.selectedDark));
                    nextBtn.setOnClickListener(v -> verifyOtp(otp, sessionToken, phone));

                } else {
                    nextBtn.setBackgroundColorCustom(getColor(R.color.unselected));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        pinView.setText(otpPreview);

    }

    private void verifyOtp(String otp, String sessionToken, String phone) {

        // 2. ডায়ালগ শুরু করুন
        if (dialog != null) {
            dialog.start();
        }

        // 3. Body রেডি করুন
        Map<String, Object> body = new HashMap<>();
        body.put("phone", phone);
        body.put("otp", otp);
        body.put("session_token", sessionToken);

        ApiService.post(ApiConfig.VERIFY_OTP, body, null, null)
                .thenAccept(apiResponse -> {
                    // UI সংক্রান্ত কাজ সবসময় Main Thread এ সম্পন্ন করুন
                    runOnUiThread(() -> {
                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        if (apiResponse.isSuccess()) {
                            Log.d(TAG, "Success: " + apiResponse.getJson());

                            // Laravel রেসপন্স থেকে Status এবং Session Token নিন
                            String userStatus = apiResponse.getString("data.status");
                            String newSessionToken = apiResponse.getString("data.session_token");

                            if ("existing_user".equals(userStatus)) {
                                // পুরাতন ইউজার -> PIN ইনপুট স্ক্রিনে পাঠান
                                //Intent intent = new Intent(this, PinVerifyActivity.class);

                            } else if ("new_user".equals(userStatus)) {

                                Bundle bundle = new Bundle();
                                bundle.putString("sessionToken", newSessionToken);
                                openActivity(RegisterActivity.class, bundle);
                            }

                            Toast.makeText(this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            Log.e(TAG, "Failed: " + apiResponse.getMessage());
                            Toast.makeText(this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .exceptionally(throwable -> {
                    // এপিআই কলে কোনো মারাত্মক এক্সসেপশন হলে হ্যান্ডেল করবে
                    runOnUiThread(() -> {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        Log.e(TAG, "API Error: " + throwable.getMessage());
                        Toast.makeText(this, "কোথাও কোনো সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                    });
                    return null;
                });

    }


}