package com.holystock.newmyoffer.activity.home;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.recyclerview.widget.RecyclerView;

import com.holystock.newmyoffer.R;

import com.holystock.newmyoffer.activity.BaseActivity;
import com.holystock.newmyoffer.controllers.BottomNavController;
import com.holystock.newmyoffer.utils.appThemes.Status;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.appcompat.app.AppCompatActivity;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;

public class HomeActivity extends BaseActivity {
    private BlurView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new Status(this).setLightStatusBar();

        /*blurView = findViewById(R.id.blurView);

        ViewGroup rootView = (ViewGroup) getWindow().getDecorView()
                .findViewById(android.R.id.content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurView.setupWith(rootView, new RenderEffectBlur())
                    .setBlurRadius(20f)
                    .setBlurAutoUpdate(true);
        } else {
            blurView.setupWith(rootView, new RenderScriptBlur(this))
                    .setBlurRadius(20f)
                    .setBlurAutoUpdate(true);
        }

        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);
*/




    }
}