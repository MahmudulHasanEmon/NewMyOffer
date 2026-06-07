package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.holystock.newmyoffer.R;

public class CustomLinearLayout extends LinearLayout {

    private GradientDrawable backgroundDrawable;

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        float radius = 0;
        int color = Color.WHITE;

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomLayout
            );

            try {

                radius = ta.getDimension(
                        R.styleable.CustomLayout_myCornerRadius,
                        0f
                );

                color = ta.getColor(
                        R.styleable.CustomLayout_solidColor,
                        Color.WHITE
                );

            } finally {
                ta.recycle();
            }
        }

        applyBackground(color, radius);
    }

    private void applyBackground(int color, float radius) {

        if (backgroundDrawable == null) {
            backgroundDrawable = new GradientDrawable();
        }

        backgroundDrawable.setColor(color);
        backgroundDrawable.setCornerRadius(radius);

        setBackground(backgroundDrawable);
    }

    // Optional runtime methods (advanced use)

    public void setCornerRadius(float radius) {
        if (backgroundDrawable != null) {
            backgroundDrawable.setCornerRadius(radius);
            invalidate();
        }
    }

    public void setSolidColor(int color) {
        if (backgroundDrawable != null) {
            backgroundDrawable.setColor(color);
            invalidate();
        }
    }
}