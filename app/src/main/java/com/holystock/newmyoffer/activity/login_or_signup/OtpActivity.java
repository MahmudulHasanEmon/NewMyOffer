package com.holystock.newmyoffer.activity.login_or_signup;

import static com.holystock.newmyoffer.utils.Helper.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.data.api.ApiConfig;
import com.holystock.newmyoffer.data.api.services.ApiService;
import com.holystock.newmyoffer.utils.Helper;
import com.holystock.newmyoffer.utils.appThemes.Status;
import com.holystock.newmyoffer.utils.dialog.LoadingDialog;
import com.holystock.newmyoffer.views.LanguageToggleButton;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends BaseActivity {

    private static LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        new Status(this).setLightStatusBar();
        dialog = new LoadingDialog(this);

        findViewById(R.id.back).setOnClickListener(v -> finish());

        findViewById(R.id.nextBtn).setOnClickListener(v -> openActivity(PinActivity.class, null));

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

        // 2. ডায়ালগ শুরু করুন
        if (dialog != null) {
            dialog.dismiss();
        }

        // 3. Body রেডি করুন
        Map<String, Object> body = new HashMap<>();
        body.put("phone", phone);
        body.put("otp", otpPreview);
        body.put("session_token", sessionToken);

        // 4. API কল করুন
        new Handler().postDelayed(() -> ApiService.post(ApiConfig.VERIFY_OTP, body, null, null)
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
                                // নতুন ইউজার -> রেজিস্ট্রেশন স্ক্রিনে পাঠান
                                //Intent intent = new Intent(this, RegisterActivity.class);
                            }

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
                }),2000);

    }
}