package com.holystock.newmyoffer.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.utils.LanguageManager;

public class LanguageToggleButton extends View {

    public interface OnToggleListener {
        void onToggle(boolean isRightSelected);
    }

    private OnToggleListener listener;

    private boolean isRightSelected = false;
    private float animationProgress = 0f;
    private ValueAnimator animator;

    private Paint bgPaint;
    private Paint selectorPaint;
    private Paint textPaint;

    private RectF bgRect;
    private RectF selectorRect;

    private String leftText = "Eng";
    private String rightText = "বাং";

    private int bgColor = Color.parseColor("#EEEEEE");
    private int selectorColor = Color.parseColor("#2E7D32");
    private int textColorActive = Color.BLACK;
    private int textColorInactive = Color.GRAY;

    private Typeface customTypeface = Typeface.DEFAULT;
    private float textSize;

    // XML থেকে স্ট্রোক উইথ রাখার জন্য ভেরিয়েবল
    private float selectorStrokeWidth;

    public LanguageToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LanguageToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        bgRect = new RectF();
        selectorRect = new RectF();

        textSize = sp(16);
        selectorStrokeWidth = dp(2); // ডিফল্ট স্ট্রোক থিকনেস ২ ডিপি

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LanguageToggleButton);
            leftText = a.getString(R.styleable.LanguageToggleButton_lang_left_text);
            rightText = a.getString(R.styleable.LanguageToggleButton_lang_right_text);
            bgColor = a.getColor(R.styleable.LanguageToggleButton_lang_bg_color, bgColor);
            selectorColor = a.getColor(R.styleable.LanguageToggleButton_lang_selector_color, selectorColor);
            textColorActive = a.getColor(R.styleable.LanguageToggleButton_lang_text_color_active, textColorActive);
            textColorInactive = a.getColor(R.styleable.LanguageToggleButton_lang_text_color_inactive, textColorInactive);
            textSize = a.getDimension(R.styleable.LanguageToggleButton_lang_text_size, textSize);

            // XML থেকে কাস্টম স্ট্রোক উইথ রিড করা
            selectorStrokeWidth = a.getDimension(R.styleable.LanguageToggleButton_lang_selector_stroke_width, selectorStrokeWidth);

            int fontResId = a.getResourceId(R.styleable.LanguageToggleButton_lang_font_family, 0);
            if (fontResId != 0) {
                try {
                    customTypeface = ResourcesCompat.getFont(context, fontResId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (leftText == null) leftText = "Eng";
            if (rightText == null) rightText = "বাং";
            a.recycle();
        }

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);

        selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectorPaint.setStyle(Paint.Style.STROKE);
        selectorPaint.setStrokeWidth(selectorStrokeWidth); // রিড করা স্ট্রোক উইথ সেট করা হলো
        selectorPaint.setColor(selectorColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);

        if (customTypeface != null) {
            textPaint.setTypeface(customTypeface);
        }
    }


    public void init(AppCompatActivity activity) {
        toggle("bn".equals(
                activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
                        .getString("language", "en")));

        setOnToggleListener(isRight ->
                LanguageManager.get().setLanguage(isRight ? "bn" : "en"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = (int) dp(140);
        int defaultHeight = (int) dp(45);

        int width = resolveSize(defaultWidth, widthMeasureSpec);
        int height = resolveSize(defaultHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = height / 2f;

        bgRect.set(0, 0, width, height);
        bgPaint.setColor(bgColor);
        canvas.drawRoundRect(bgRect, radius, radius, bgPaint);

        float selectorWidth = width / 2f;

        // স্ট্রোকের থিকনেস অনুযায়ী প্যাডিং ব্যালেন্স করা হয়েছে যেন বর্ডার বাইরে কেটে না যায়
        float padding = dp(2) + (selectorStrokeWidth / 2f);

        float startX = padding + (animationProgress * (width / 2f - padding * 2));
        float endX = startX + selectorWidth - padding;

        selectorRect.set(startX, padding, endX, height - padding);

        bgPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(selectorRect, radius - padding, radius - padding, bgPaint);

        canvas.drawRoundRect(selectorRect, radius - padding, radius - padding, selectorPaint);

        float centerY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f);

        textPaint.setColor(isRightSelected ? textColorInactive : textColorActive);
        textPaint.setFakeBoldText(!isRightSelected);
        canvas.drawText(leftText, width / 4f, centerY, textPaint);

        textPaint.setColor(isRightSelected ? textColorActive : textColorInactive);
        textPaint.setFakeBoldText(isRightSelected);
        canvas.drawText(rightText, (width / 4f) * 3, centerY, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            toggle(!isRightSelected);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void toggle(boolean rightSide) {
        if (this.isRightSelected == rightSide) return;
        this.isRightSelected = rightSide;

        if (animator != null) animator.cancel();

        animator = ValueAnimator.ofFloat(animationProgress, isRightSelected ? 1f : 0f);
        animator.setDuration(250);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            animationProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();

        if (listener != null) {
            listener.onToggle(isRightSelected);
        }
    }

    // রানটাইমে জাভা থেকে স্ট্রোক উইথ পরিবর্তন করার মেথড
    public void setSelectorStrokeWidth(float widthInDp) {
        this.selectorStrokeWidth = dp(widthInDp);
        if (selectorPaint != null) {
            selectorPaint.setStrokeWidth(selectorStrokeWidth);
            invalidate();
        }
    }

    public void setTextSize(float sizeInSp) {
        this.textSize = sp(sizeInSp);
        if (textPaint != null) {
            textPaint.setTextSize(textSize);
            invalidate();
        }
    }

    public boolean isRightSelected() {
        return isRightSelected;
    }

    public void setOnToggleListener(OnToggleListener l) {
        this.listener = l;
    }

    private float dp(float value) {
        return value * getResources().getDisplayMetrics().density;
    }

    private float sp(float value) {
        return value * getResources().getDisplayMetrics().scaledDensity;
    }
}