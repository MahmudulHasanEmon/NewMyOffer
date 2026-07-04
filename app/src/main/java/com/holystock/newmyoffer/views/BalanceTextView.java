package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

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
        String text = ta.getString(R.styleable.BalanceTextView_customText);
        text = text != null ? text : getText().toString();

        int color = ta.getColor(
                R.styleable.BalanceTextView_customTextColor,
                getResources().getColor(R.color.grey)
        );

        float size = ta.getDimension(
                R.styleable.BalanceTextView_customTextSize,
                getResources().getDimension(R.dimen._14sp)
        );

        int style = ta.getInt(
                R.styleable.BalanceTextView_customTextStyle,
                0
        );

        setText(text);
        setTextColor(color);
        setTextSize(pxToSp(size));
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTypeface(ResourcesCompat.getFont(
                getContext(),
                R.font.bangla_medium
        ));

        if (style == 1) {
            setTypeface(Typeface.DEFAULT_BOLD);
        }

        ta.recycle();
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }
}