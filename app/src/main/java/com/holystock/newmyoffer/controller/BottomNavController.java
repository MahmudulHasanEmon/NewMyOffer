package com.holystock.newmyoffer.controller;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.holystock.newmyoffer.R;

public class BottomNavController {

    public interface BottomNavListener {
        void onTabSelected(int position, String title);
    }

    private final BottomNavListener listener;

    private final int selectedColor;
    private final int normalColor;

    private static final float SELECTED_ICON_SCALE = 1.05f;
    private static final float SELECTED_TEXT_SCALE = 1.05f;
    private static final float NORMAL_SCALE = 1.0f;

    private ImageView imgHome, imgChat, imgScan, imgInbox;
    private TextView txtHome, txtChat, txtScan, txtInbox;

    private LinearLayout navHome, navChat, navScan, navInbox;

    public BottomNavController(Activity activity, BottomNavListener listener) {

        this.listener = listener;

        View root = activity.getWindow().getDecorView();

        selectedColor = ContextCompat.getColor(activity, R.color.primary);
        normalColor = ContextCompat.getColor(activity, R.color.black);

        // Navigation Layouts
        navHome = root.findViewById(R.id.navHome);
        navChat = root.findViewById(R.id.navChat);
        navScan = root.findViewById(R.id.navScan);
        navInbox = root.findViewById(R.id.navInbox);

        // Icons
        imgHome = root.findViewById(R.id.imgHome);
        imgChat = root.findViewById(R.id.imgChat);
        imgScan = root.findViewById(R.id.imgScan);
        imgInbox = root.findViewById(R.id.imgInbox);

        // Titles
        txtHome = root.findViewById(R.id.txtHome);
        txtChat = root.findViewById(R.id.txtChat);
        txtScan = root.findViewById(R.id.txtScan);
        txtInbox = root.findViewById(R.id.txtInbox);

        setupClicks();

        // Default Selected
        selectTab(0);
    }

    private void setupClicks() {

        navHome.setOnClickListener(v -> selectTab(0));
        navChat.setOnClickListener(v -> selectTab(1));
        navScan.setOnClickListener(v -> selectTab(2));
        navInbox.setOnClickListener(v -> selectTab(3));
    }

    public void selectTab(int position) {

        resetAll();

        String title = "";

        switch (position) {

            case 0:
                imgHome.setImageResource(R.drawable.home_filled);
                txtHome.setTextColor(selectedColor);
                imgHome.setColorFilter(selectedColor);

                animateSelection(imgHome, txtHome);
                title = txtHome.getText().toString();
                break;

            case 1:
                imgChat.setImageResource(R.drawable.chat_filled);
                txtChat.setTextColor(selectedColor);
                imgChat.setColorFilter(selectedColor);
                animateSelection(imgChat, txtChat);
                title = txtChat.getText().toString();
                break;

            case 2:
                imgScan.setImageResource(R.drawable.scan);
                txtScan.setTextColor(selectedColor);
                imgScan.setColorFilter(selectedColor);

                animateSelection(imgScan, txtScan);
                title = txtScan.getText().toString();
                break;

            case 3:
                imgInbox.setImageResource(R.drawable.inbox_filled);
                txtInbox.setTextColor(selectedColor);
                imgInbox.setColorFilter(selectedColor);
                animateSelection(imgInbox, txtInbox);
                title = txtInbox.getText().toString();
                break;
        }

        if (listener != null) {
            listener.onTabSelected(position, title);
        }
    }

    private void resetAll() {

        // Default icons
        imgHome.setImageResource(R.drawable.home);
        imgChat.setImageResource(R.drawable.chat);
        imgScan.setImageResource(R.drawable.scan);
        imgInbox.setImageResource(R.drawable.inbox);

        imgHome.setColorFilter(normalColor);
        imgChat.setColorFilter(normalColor);
        imgScan.setColorFilter(normalColor);
        imgInbox.setColorFilter(normalColor);

        // Reset text color
        txtHome.setTextColor(normalColor);
        txtChat.setTextColor(normalColor);
        txtScan.setTextColor(normalColor);
        txtInbox.setTextColor(normalColor);

        // Reset scale
        resetScaleAll();
    }

    private void animateSelection(ImageView imageView, TextView textView) {

        imageView.animate()
                .scaleX(SELECTED_ICON_SCALE)
                .scaleY(SELECTED_ICON_SCALE)
                .setDuration(150)
                .start();

        textView.animate()
                .scaleX(SELECTED_TEXT_SCALE)
                .scaleY(SELECTED_TEXT_SCALE)
                .setDuration(150)
                .start();
    }

    private void resetScaleAll() {

        // Icons
        imgHome.setScaleX(NORMAL_SCALE);
        imgHome.setScaleY(NORMAL_SCALE);

        imgChat.setScaleX(NORMAL_SCALE);
        imgChat.setScaleY(NORMAL_SCALE);

        imgScan.setScaleX(NORMAL_SCALE);
        imgScan.setScaleY(NORMAL_SCALE);

        imgInbox.setScaleX(NORMAL_SCALE);
        imgInbox.setScaleY(NORMAL_SCALE);

        // Texts
        txtHome.setScaleX(NORMAL_SCALE);
        txtHome.setScaleY(NORMAL_SCALE);

        txtChat.setScaleX(NORMAL_SCALE);
        txtChat.setScaleY(NORMAL_SCALE);

        txtScan.setScaleX(NORMAL_SCALE);
        txtScan.setScaleY(NORMAL_SCALE);

        txtInbox.setScaleX(NORMAL_SCALE);
        txtInbox.setScaleY(NORMAL_SCALE);
    }
}