package com.holystock.newmyoffer.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.holystock.newmyoffer.R;

public class Toolbar extends ConstraintLayout {

    private MaterialToolbar toolbar;

    public Toolbar(Context context) {
        super(context);
        init(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        LayoutInflater.from(context).inflate(R.layout.view_toolbar, this, true);

        toolbar = findViewById(R.id.toolbar);

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);

            toolbar.setTitle(ta.getString(R.styleable.Toolbar_toolbarTitle));

            toolbar.setTitleCentered(
                    ta.getBoolean(R.styleable.Toolbar_titleCentered, true)
            );

            int textSize = ta.getDimensionPixelSize(
                    R.styleable.Toolbar_titleTextSize,
                    18
            );

            toolbar.post(() -> {

                for (int i = 0; i < toolbar.getChildCount(); i++) {

                    View child = toolbar.getChildAt(i);

                    if (child instanceof TextView) {

                        TextView tv = (TextView) child;

                        if (tv.getText().equals(toolbar.getTitle())) {
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                            break;
                        }
                    }
                }
            });

            toolbar.setTitleTextColor(
                    ta.getColor(
                            R.styleable.Toolbar_titleColor,
                            ContextCompat.getColor(context, android.R.color.white)
                    )
            );

            int menuRes = ta.getResourceId(R.styleable.Toolbar_menu, 0);

            if (menuRes != 0) {
                toolbar.inflateMenu(menuRes);
            }


            toolbar.setBackgroundColor(
                    ta.getColor(
                            R.styleable.Toolbar_toolbarBackground,
                            ContextCompat.getColor(context, R.color.primary)
                    )
            );

            Drawable nav = ta.getDrawable(R.styleable.Toolbar_navigationIcon);

            if (nav != null) {
                toolbar.setNavigationIcon(nav);
            }

            toolbar.setNavigationIconTint(
                    ta.getColor(
                            R.styleable.Toolbar_navigationTint,
                            ContextCompat.getColor(context, android.R.color.white)
                    )
            );

            boolean showBack = ta.getBoolean(
                    R.styleable.Toolbar_showBackButton,
                    true
            );

            if (!showBack) {
                toolbar.setNavigationIcon(null);
            }

            int height = ta.getDimensionPixelSize(
                    R.styleable.Toolbar_toolbarHeight,
                    (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            56,
                            getResources().getDisplayMetrics()
                    )
            );

            ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
            lp.height = height;
            toolbar.setLayoutParams(lp);

            ta.recycle();
        }

        toolbar.setNavigationOnClickListener(v -> {
            if (context instanceof Activity) {
                ((Activity) context).onBackPressed();
            }
        });
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setTitleCentered(boolean centered) {
        toolbar.setTitleCentered(centered);
    }

    public void showBackButton(boolean show) {
        if (show) {
            toolbar.setNavigationIcon(R.drawable.outline_arrow_back_24);
        } else {
            toolbar.setNavigationIcon(null);
        }
    }

    public void setOnMenuItemClickListener(MaterialToolbar.OnMenuItemClickListener listener) {
        toolbar.setOnMenuItemClickListener(listener);
    }

    public void setNavigationIcon(Drawable drawable) {
        toolbar.setNavigationIcon(drawable);
    }

    public void setMenu(int menuRes) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(menuRes);
    }

    public void clearMenu() {
        toolbar.getMenu().clear();
    }

    public void setNavigationClickListener(OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    public MaterialToolbar getToolbar() {
        return toolbar;
    }
}