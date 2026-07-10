package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.holystock.newmyoffer.R;

public class BottomNextButtonView extends RelativeLayout {

    private TextView textView;
    private RelativeLayout layout;
    private boolean buttonClickable = false;

    public BottomNextButtonView(Context context) {
        super(context);
        init(context);
    }

    public BottomNextButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNextButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context)
                .inflate(R.layout.next_bottom_view, this, true);

        textView = findViewById(R.id.tvButtonText);
        layout = findViewById(R.id.buttonTint);

        this.setButtonClickable(false);

    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setButtonTint(int colorRes) {
        layout.setBackgroundTintList(
                ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), colorRes)
                )
        );
    }

    public void setButtonClickable(boolean clickable) {
        this.buttonClickable = clickable;

        layout.setBackgroundTintList(
                ColorStateList.valueOf(
                        ContextCompat.getColor(
                                getContext(),
                                clickable ? R.color.selectedDark : R.color.unselected
                        )
                )
        );
    }

    public boolean isButtonClickable() {
        return buttonClickable;
    }


}