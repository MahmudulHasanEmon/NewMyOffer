package com.holystock.newmyoffer.utils.helpers;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

public class OtpHelper {

    private final EditText[] otpFields;
    private final Context context;
    private boolean isPasting = false;

    public OtpHelper(Context context, EditText[] fields) {
        this.context = context;
        this.otpFields = fields;
        setupOtpLogic();
    }

    private void setupOtpLogic() {
        for (int i = 0; i < otpFields.length; i++) {
            final int index = i;

            // 1. Advance / Next Focus & Paste Listener
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isPasting) return;

                    // Paste Detector: একাধিক ডিজিট পেস্ট করা হলে
                    if (s.length() > 1) {
                        handlePaste(s.toString().replaceAll("[^0-9]", ""));
                        return;
                    }

                    // Advance: ১টি ডিজিট টাইপ করলে পরের বক্সে ফোকাস যাবে
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        otpFields[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            // 2. Backspace / Delete Listener
            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (otpFields[index].getText().toString().isEmpty() && index > 0) {
                        otpFields[index - 1].requestFocus();
                        otpFields[index - 1].setText("");
                        return true;
                    }
                }
                return false;
            });
        }
    }

    // ==========================================
    // 📋 PASTE HANDLER (অটোমেটিক ৬ বক্সে বসবে)
    // ==========================================
    public void handlePaste(String pastedText) {
        if (pastedText == null || pastedText.isEmpty()) return;

        isPasting = true;
        char[] digits = pastedText.toCharArray();

        for (int i = 0; i < otpFields.length; i++) {
            if (i < digits.length) {
                otpFields[i].setText(String.valueOf(digits[i]));
            }
        }

        // শেষ পূরণ হওয়া ডিজিটের পর ফোকাস রাখা
        int targetIndex = Math.min(digits.length, otpFields.length) - 1;
        if (targetIndex >= 0) {
            otpFields[targetIndex].requestFocus();
            otpFields[targetIndex].setSelection(otpFields[targetIndex].getText().length());
        }

        isPasting = false;
    }

    // ==========================================
    // 🧹 CLEAR ALL OTP FIELDS
    // ==========================================
    public void clearOtp() {
        for (EditText field : otpFields) {
            field.setText("");
        }
        if (otpFields.length > 0) {
            otpFields[0].requestFocus(); // প্রথম বক্সে ফোকাস ফেরত আনা
        }
    }

    // ==========================================
    // 🎯 GET COMPLETE OTP STRING
    // ==========================================
    public String getOtp() {
        StringBuilder sb = new StringBuilder();
        for (EditText field : otpFields) {
            sb.append(field.getText().toString().trim());
        }
        return sb.toString();
    }

    // Check if full 6-digit entered
    public boolean isValid() {
        return getOtp().length() == otpFields.length;
    }
}
