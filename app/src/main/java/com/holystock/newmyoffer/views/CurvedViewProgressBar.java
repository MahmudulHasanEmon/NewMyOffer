/*
package com.holystock.newmyoffer.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.holystock.newmyoffer.R;

public class CurvedViewProgressBar extends View {
    private Paint fillPaint;
    private Paint backgroundPaint;
    private Path curvePath;
    private int progress = 2;
    private boolean isComplete = false;
    private boolean isTouching = false;

    public CurvedViewProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Default colors
        int progressColor = Color.parseColor("#4CAF50");
        int bgColor = Color.parseColor("#BDBDBD");

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CurvedViewProgressBar);
            progressColor = a.getColor(R.styleable.CurvedViewProgressBar_progressColor, progressColor);
            bgColor = a.getColor(R.styleable.CurvedViewProgressBar_backgroundColor, bgColor);
            a.recycle();
        }

        fillPaint.setColor(progressColor);
        backgroundPaint.setColor(bgColor);

        curvePath = new Path();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Draw background curve
        curvePath.reset();
        curvePath.moveTo(0, 200);
        curvePath.quadTo(width / 2f, 0, width, 200);
        curvePath.lineTo(width, height);
        curvePath.lineTo(0, height);
        curvePath.close();

        canvas.drawPath(curvePath, backgroundPaint);

        // Clip and draw progress
        canvas.save();
        canvas.clipPath(curvePath);

        float progressWidth = (width * progress) / 100f;
        canvas.drawRect(0, 0, progressWidth, height, fillPaint);

        canvas.restore();



        // Update progress if touch is ongoing
        if (isTouching && !isComplete) {
            postDelayed(() -> {
                if (progress < 100) {
                    progress += 2;
                    if (progress >= 100) {
                        progress = 100;
                        isComplete = true;
                    }
                    invalidate();
                }
            }, 5);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouching = false;
                if (!isComplete) {
                    progress = 2;
                    invalidate();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    // Public methods to control touch manually
    public void startTouch() {
        isTouching = true;
        invalidate();
    }

    public void releaseTouch() {
        isTouching = false;
        if (!isComplete) {
            progress = 2;
            invalidate();
        }
    }

    // Set colors programmatically
    public void setProgressColor(int color) {
        fillPaint.setColor(color);
        invalidate();
    }

    public void setBackgroundColorCustom(int color) {
        backgroundPaint.setColor(color);
        invalidate();
    }

    // Get current progress
    public int getProgress() {
        return progress;
    }
}

*/
