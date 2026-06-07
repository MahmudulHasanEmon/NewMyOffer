package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.holystock.newmyoffer.R;

public class BorderView extends RelativeLayout {

    private GradientDrawable drawable;

    public BorderView(Context context) {
        super(context);
        init(context, null);
    }

    public BorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        int borderColor = 0xFF6200EE;
        int bgColor = 0xFFFFFFFF;
        float borderWidth = dpToPx(1.5f);
        float radius = dpToPx(5);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.BorderView
            );

            borderColor = ta.getColor(
                    R.styleable.BorderView_borderColor,
                    borderColor
            );

            bgColor = ta.getColor(
                    R.styleable.BorderView_bgColor,
                    bgColor
            );

            borderWidth = ta.getDimension(
                    R.styleable.BorderView_borderWidth,
                    borderWidth
            );

            radius = ta.getDimension(
                    R.styleable.BorderView_cornerRadius,
                    radius
            );

            ta.recycle();
        }

        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(bgColor);
        drawable.setCornerRadius(radius);
        drawable.setStroke((int) borderWidth, borderColor);

        setBackground(drawable);
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    public void setBorderColor(int color) {
        drawable.setStroke((int) dpToPx(1.5f), color);
        invalidate();
    }

    public void setBackgroundColorCustom(int color) {
        drawable.setColor(color);
        invalidate();
    }
}
