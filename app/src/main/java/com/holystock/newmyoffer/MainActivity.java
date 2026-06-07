package com.holystock.newmyoffer;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.activity.home.page.ChatPage;
import com.holystock.newmyoffer.activity.home.page.HomePage;
import com.holystock.newmyoffer.activity.home.page.InboxPage;
import com.holystock.newmyoffer.controllers.BottomNavController;
import com.holystock.newmyoffer.utils.appThemes.Status;

import eightbitlab.com.blurview.BlurAlgorithm;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends BaseActivity
        implements BottomNavController.BottomNavListener {

    private static final float BLUR_RADIUS = 7.5f;

    private BlurView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        if (savedInstanceState == null) {
            loadFragment(new HomePage());
        }
    }

    private void initUi() {

        new Status(this).setLightStatusBar();

        new BottomNavController(
                this,
                this
        );

        blurView = findViewById(R.id.blurView);

        setupBlurView();

    }

    private void setupBlurView() {

        ViewGroup rootView =
                findViewById(android.R.id.content);

        BlurAlgorithm algorithm;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            algorithm = new RenderEffectBlur();

        } else {

            algorithm = new RenderScriptBlur(this);
        }

        blurView.setupWith(
                        rootView,
                        algorithm
                )
                .setBlurRadius(BLUR_RADIUS)
                .setBlurAutoUpdate(true);

        blurView.setOutlineProvider(
                ViewOutlineProvider.BACKGROUND
        );

        blurView.setClipToOutline(true);
    }

    @Override
    public void onTabSelected(
            int position,
            @NonNull String title
    ) {

        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new HomePage();
                break;

            case 1:
                fragment = new ChatPage();
                break;

            case 3:
                fragment = new InboxPage();
                break;
        }

        if (fragment != null) {
            loadFragment(fragment);
        }
    }

    private void loadFragment(
            @NonNull Fragment fragment
    ) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragment_container,
                        fragment
                )
                .commit();
    }

    @Override
    protected void onDestroy() {

        if (blurView != null) {
            blurView.setBlurAutoUpdate(false);
        }

        super.onDestroy();
    }
}