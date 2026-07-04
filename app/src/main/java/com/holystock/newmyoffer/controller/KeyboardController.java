package com.holystock.newmyoffer.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.holystock.newmyoffer.R;

public class KeyboardController {

    public interface OnKeyPressListener {
        void onKeyPressed(String value);
    }

    public interface OnChangeViewListener {
        void onViewChange(boolean bool);
    }

    private final Context context;
    private final ViewGroup rootView;
    private final TextView tvDisplay;
    private final OnKeyPressListener listener;
    private final OnChangeViewListener viewListener;

    private View keyboardView;
    private boolean isShowing = false;

    private int maxLength = 5;
    private String value = "";

    public KeyboardController(
            @NonNull Context context,
            @NonNull ViewGroup rootView,
            @NonNull TextView tvDisplay,
            @NonNull OnKeyPressListener listener,
            @NonNull OnChangeViewListener viewListener
    ) {
        this.context = context;
        this.rootView = rootView;
        this.tvDisplay = tvDisplay;
        this.listener = listener;
        this.viewListener = viewListener;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void showKeyboard() {

        if (isShowing) return;

        keyboardView = LayoutInflater.from(context)
                .inflate(R.layout.keyboard_view, rootView, false);

        initKeys();

        keyboardView.setAlpha(0f);
        keyboardView.setTranslationY(300f);

        rootView.addView(keyboardView);

        keyboardView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(800)
                .start();

        isShowing = true;

        updateKeyboardIcon(true);

        if (viewListener !=null){
            viewListener.onViewChange(true);
        }

    }

    public void hideKeyboard() {

        if (!isShowing || keyboardView == null) return;

        if (viewListener !=null){
            viewListener.onViewChange(false);
        }

        keyboardView.animate()
                .translationY(keyboardView.getHeight())
                .alpha(0f)
                .setDuration(400)
                .withEndAction(() -> {
                    rootView.removeView(keyboardView);
                    keyboardView = null;
                    isShowing = false;
                    updateKeyboardIcon(false);

                })
                .start();


    }

    public boolean isShowing() {
        return isShowing;
    }

    public void toggleKeyboard() {
        if (isShowing()) hideKeyboard();
        else showKeyboard();
    }

    private void initKeys() {

        int[] keyIds = {
                R.id.btn0,
                R.id.btn1,
                R.id.btn2,
                R.id.btn3,
                R.id.btn4,
                R.id.btn5,
                R.id.btn6,
                R.id.btn7,
                R.id.btn8,
                R.id.btn9
        };

        for (int id : keyIds) {

            TextView key = keyboardView.findViewById(id);

            key.setOnClickListener(v -> {

                String digit = key.getText().toString();

                if (value.length() < maxLength) {

                    value += digit;

                    initDisplay();
                    if (listener != null) {
                        listener.onKeyPressed(value);
                    }

                } else if (value.length() == maxLength) {

                }
            });
        }

        // Backspace Button (optional)
        View btnDelete = keyboardView.findViewById(R.id.btnDelete);

        if (btnDelete != null) {
            btnDelete.setOnClickListener(v -> clearValue());
        }

        // Submit Button (optional)
        View btnSubmit = keyboardView.findViewById(R.id.btnSubmit);

        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> toggleKeyboard());
        }

        tvDisplay.setOnClickListener(v -> {
            v.setEnabled(false);
            toggleKeyboard();
            v.postDelayed(() -> v.setEnabled(true), 500);
        });

    }

    public void initDisplay(){

        if (!value.isEmpty()){
            tvDisplay.setLetterSpacing(0.3F);
            tvDisplay.setTextColor(context.getColor(R.color.black));

            String dot = "\u2B24";
            tvDisplay.setText(dot.repeat(value.length()));

        }else{
            tvDisplay.setText("পিন নাম্বার লিখুন");
            tvDisplay.setTextColor(context.getColor(R.color.grey));
        }

    }

    private void updateKeyboardIcon(boolean isKeyboardVisible) {
        tvDisplay.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock_24dp,
                0,
                isKeyboardVisible
                        ? R.drawable.keyboard_hide_24dp
                        : R.drawable.keyboard_24dp,
                0
        );
    }

    public String getValue() {
        return value;
    }

    public void clearValue() {
        value = "";

        initDisplay();
        if (listener != null) {
            listener.onKeyPressed(value);
        }
    }

    public void removeLast() {

        if (!value.isEmpty()) {

            value = value.substring(0, value.length() - 1);

            initDisplay();
            if (listener != null) {
                listener.onKeyPressed(value);
            }
        }
    }

    public void setValue(String value) {
        this.value = value == null ? "" : value;

        if (listener != null) {
            listener.onKeyPressed(this.value);
        }
    }

}