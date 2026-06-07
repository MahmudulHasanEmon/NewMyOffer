package com.holystock.newmyoffer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.holystock.newmyoffer.R;

public class IconTextView extends AppCompatTextView {

    public static final int START = 0;
    public static final int TOP = 1;
    public static final int END = 2;
    public static final int BOTTOM = 3;

    private Drawable iconDrawable;
    private Drawable finalIconDrawable;
    private int iconAlignment = START;

    public IconTextView(Context context) {
        super(context);
        init(context, null);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IconTextView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(
            Context context,
            @Nullable AttributeSet attrs
    ) {

        if (attrs == null) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView
        );

        // =====================
        // TEXT
        // =====================

        String text = ta.getString(
                R.styleable.IconTextView_itvText
        );

        if (text != null) {
            setText(text);
        }

        setTextColor(
                ta.getColor(
                        R.styleable.IconTextView_itvTextColor,
                        getCurrentTextColor()
                )
        );

        float textSize = ta.getDimension(
                R.styleable.IconTextView_itvTextSize,
                getTextSize()
        );

        setTextSize(
                pxToSp(context, textSize)
        );

        int fontRes = ta.getResourceId(
                R.styleable.IconTextView_itvFontFamily,
                0
        );

        if (fontRes != 0) {
            setTypeface(
                    ResourcesCompat.getFont(
                            context,
                            fontRes
                    )
            );
        }

        // =====================
        // ICON
        // =====================

        int iconRes = ta.getResourceId(
                R.styleable.IconTextView_itvIcon,
                0
        );

        int iconColor = ta.getColor(
                R.styleable.IconTextView_itvIconColor,
                0
        );

        int iconSize = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconSize,
                -1
        );

        int iconWidth = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconWidth,
                -1
        );

        int iconHeight = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconHeight,
                -1
        );

        int iconPaddingStart = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconPaddingStart,
                0
        );

        int iconPaddingTop = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconPaddingTop,
                0
        );

        int iconPaddingEnd = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconPaddingEnd,
                0
        );

        int iconPaddingBottom = ta.getDimensionPixelSize(
                R.styleable.IconTextView_itvIconPaddingBottom,
                0
        );

        iconAlignment = ta.getInt(
                R.styleable.IconTextView_itvIconAlignment,
                START
        );

        if (iconRes != 0) {

            Drawable drawable =
                    AppCompatResources.getDrawable(
                            context,
                            iconRes
                    );

            if (drawable != null) {

                drawable =
                        DrawableCompat.wrap(
                                drawable
                        ).mutate();

                if (iconColor != 0) {

                    DrawableCompat.setTint(
                            drawable,
                            iconColor
                    );
                }

                int width;
                int height;

                if (iconSize > 0) {

                    width = iconSize;
                    height = iconSize;

                } else {

                    width = iconWidth > 0
                            ? iconWidth
                            : drawable.getIntrinsicWidth();

                    height = iconHeight > 0
                            ? iconHeight
                            : drawable.getIntrinsicHeight();
                }

                drawable.setBounds(
                        0,
                        0,
                        width,
                        height
                );

                iconDrawable = drawable;

                if (iconPaddingStart > 0
                        || iconPaddingTop > 0
                        || iconPaddingEnd > 0
                        || iconPaddingBottom > 0) {

                    InsetDrawable insetDrawable =
                            new InsetDrawable(
                                    drawable,
                                    iconPaddingStart,
                                    iconPaddingTop,
                                    iconPaddingEnd,
                                    iconPaddingBottom
                            );

                    insetDrawable.setBounds(
                            0,
                            0,
                            width
                                    + iconPaddingStart
                                    + iconPaddingEnd,
                            height
                                    + iconPaddingTop
                                    + iconPaddingBottom
                    );

                    finalIconDrawable = insetDrawable;

                } else {

                    finalIconDrawable = drawable;
                }

                applyIcon();
            }
        }

        ta.recycle();
    }

    private void applyIcon() {

        Drawable start = null;
        Drawable top = null;
        Drawable end = null;
        Drawable bottom = null;

        Drawable drawable =
                finalIconDrawable != null
                        ? finalIconDrawable
                        : iconDrawable;

        switch (iconAlignment) {

            case TOP:
                top = drawable;
                break;

            case END:
                end = drawable;
                break;

            case BOTTOM:
                bottom = drawable;
                break;

            default:
                start = drawable;
                break;
        }

        setCompoundDrawablesRelative(
                start,
                top,
                end,
                bottom
        );
    }

    private float pxToSp(
            Context context,
            float px
    ) {

        return px /
                context.getResources()
                        .getDisplayMetrics()
                        .scaledDensity;
    }

    // =====================
    // PUBLIC METHODS
    // =====================

    public void setIcon(int drawableRes) {

        iconDrawable =
                AppCompatResources.getDrawable(
                        getContext(),
                        drawableRes
                );

        if (iconDrawable != null) {

            iconDrawable.setBounds(
                    0,
                    0,
                    iconDrawable.getIntrinsicWidth(),
                    iconDrawable.getIntrinsicHeight()
            );

            finalIconDrawable = iconDrawable;

            applyIcon();
        }
    }

    public void setIconAlignment(int alignment) {

        iconAlignment = alignment;

        applyIcon();
    }

    public void setIconColor(int color) {

        if (iconDrawable == null) {
            return;
        }

        iconDrawable =
                DrawableCompat.wrap(
                        iconDrawable
                ).mutate();

        DrawableCompat.setTint(
                iconDrawable,
                color
        );

        finalIconDrawable = iconDrawable;

        applyIcon();
    }

    public void setIconSize(int sizePx) {

        if (iconDrawable == null) {
            return;
        }

        iconDrawable.setBounds(
                0,
                0,
                sizePx,
                sizePx
        );

        finalIconDrawable = iconDrawable;

        applyIcon();
    }

    public void setIconSize(
            int widthPx,
            int heightPx
    ) {

        if (iconDrawable == null) {
            return;
        }

        iconDrawable.setBounds(
                0,
                0,
                widthPx,
                heightPx
        );

        finalIconDrawable = iconDrawable;

        applyIcon();
    }

    public void removeIcon() {

        iconDrawable = null;
        finalIconDrawable = null;

        setCompoundDrawablesRelative(
                null,
                null,
                null,
                null
        );
    }
}