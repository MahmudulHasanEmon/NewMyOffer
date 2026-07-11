package com.holystock.newmyoffer.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;

public class Helper {

    public static final int[] lightColors = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#D1C4E9"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#BBDEFB"),
            Color.parseColor("#B3E5FC"),
            Color.parseColor("#B2EBF2"),
            Color.parseColor("#C8E6C9"),
            Color.parseColor("#DCEDC8"),
            Color.parseColor("#FFF9C4"),
            Color.parseColor("#FFE0B2"),
            Color.parseColor("#FFCCBC")
    };

    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position < -1) {
                page.setAlpha(0f);
            } else if (position <= 0) {
                page.setAlpha(1f);
                page.setTranslationX(0f);
                page.setScaleX(1f);
                page.setScaleY(1f);
            } else if (position <= 1) {
                page.setAlpha(1 - position);
                page.setTranslationX(page.getWidth() * -position);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else {
                page.setAlpha(0f);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String formatAmount(
            Object value,
            int decimal,
            boolean symbol
    ) {

        double amount = parseDouble(value);

        // If zero
        if (amount == 0) {

            String result;

            if (decimal == 0) {
                result = "0";
            } else {
                StringBuilder zeros = new StringBuilder();

                for (int i = 0; i < decimal; i++) {
                    zeros.append("0");
                }

                result = "0." + zeros;
            }

            return symbol ? "৳" + result : result;
        }

        // Build pattern
        StringBuilder pattern = new StringBuilder("#,##0");

        if (decimal > 0) {

            pattern.append(".");

            for (int i = 0; i < decimal; i++) {
                pattern.append("0");
            }
        }

        DecimalFormat formatter = new DecimalFormat(
                pattern.toString()
        );

        String result = formatter.format(amount);

        return symbol ? "৳" + result : result;
    }

    public static double parseDouble(Object value) {

        if (value == null) {
            return 0;
        }

        try {
            return Double.parseDouble(
                    value.toString()
                            .replace(",", "")
                            .trim()
            );
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isBangladeshiMobile(String phone) {

        return phone.matches("^01[3-9]\\d{8}$");
    }

    public static String getNumericValue(String text) {
        return text.replaceAll("[^0-9]", "");
    }
}
