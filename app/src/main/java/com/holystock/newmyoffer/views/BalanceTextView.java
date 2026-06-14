package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.holystock.newmyoffer.R;

public class BalanceTextView extends AppCompatTextView {

    public BalanceTextView(Context context) {
        super(context);
        init(context, null);
    }

    public BalanceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs == null) return;

        TypedArray ta = context.obtainStyledAttributes(
                attrs,
                R.styleable.BalanceTextView
        );

        int color = ta.getColor(
                R.styleable.BalanceTextView_customTextColor,
                getCurrentTextColor()
        );

        float size = ta.getDimension(
                R.styleable.BalanceTextView_customTextSize,
                getTextSize()
        );

        int style = ta.getInt(
                R.styleable.BalanceTextView_customTextStyle,
                0
        );

        setTextColor(color);
        setTextSize(pxToSp(size));

        if (style == 1) {
            setTypeface(Typeface.DEFAULT_BOLD);
        }

        ta.recycle();
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }
}