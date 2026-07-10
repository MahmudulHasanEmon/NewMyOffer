package com.holystock.newmyoffer.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.holystock.newmyoffer.R;

public class HoldAnimatedButtonView extends View {

    public interface OnCompleteListener {
        void onComplete();
    }

    private OnCompleteListener listener;

    private float progress = 0.03f;
    private Paint fillPaint;
    private Paint arcPaint;
    private Paint titlePaint;
    private Paint messagePaint;
    private Paint shadowPaint;

    private Path path;
    private RectF rect;
    private RectF arcRect;
    private RectF cornerRect;

    private String message = "";
    private ValueAnimator animator;

    private int backgroundColor = Color.parseColor("#1565C0");
    private int strokeColor = Color.parseColor("#1565C0");
    private float strokeWidth;

    // XML থেকে শ্যাডো কন্ট্রোল করার ভেরিয়েবলস
    private float strokeShadowRadius = 6f; // ডিফল্ট ব্লার রেডিয়াস
    private int strokeShadowColor = Color.parseColor("#66000000"); // ডিফল্ট হালকা কালো শ্যাডো

    public HoldAnimatedButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HoldAnimatedButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // স্ট্রোক ও শ্যাডো লেয়ার রেন্ডার করার জন্য সফটওয়্যার মোড এনাবেলড
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        strokeWidth = dp(10);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HoldAnimatedButtonView);
            backgroundColor = a.getColor(R.styleable.HoldAnimatedButtonView_btn_background_color, backgroundColor);
            strokeColor = a.getColor(R.styleable.HoldAnimatedButtonView_btn_stroke_color, strokeColor);
            strokeWidth = a.getDimension(R.styleable.HoldAnimatedButtonView_btn_stroke_width, strokeWidth);

            // XML থেকে কাস্টম শ্যাডো প্রোপার্টি রিড করা
            strokeShadowRadius = a.getFloat(R.styleable.HoldAnimatedButtonView_btn_stroke_shadow_radius, strokeShadowRadius);
            strokeShadowColor = a.getColor(R.styleable.HoldAnimatedButtonView_btn_stroke_shadow_color, strokeShadowColor);

            a.recycle();
        }

        path = new Path();
        rect = new RectF();
        arcRect = new RectF();
        cornerRect = new RectF();

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(backgroundColor);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setStrokeCap(Paint.Cap.BUTT);
        arcPaint.setStrokeJoin(Paint.Join.MITER);
        arcPaint.setColor(strokeColor);

        // স্ট্রোকের ঠিক নিচে এবং দুই পাশে শ্যাডো ফেলার জন্য X=0, Y=3 (নিচের দিকে ড্রপ) সেট করা হয়েছে
        arcPaint.setShadowLayer(strokeShadowRadius, 0, 3, strokeShadowColor);

        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setColor(Color.WHITE);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(sp(18));

        messagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        messagePaint.setColor(Color.WHITE);
        messagePaint.setFakeBoldText(true);
        messagePaint.setTextAlign(Paint.Align.CENTER);
        messagePaint.setTextSize(sp(20));

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(backgroundColor);
        shadowPaint.setShadowLayer(8, 0, 2, 0x55000000);

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);

        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (progress >= 1f && listener != null) {
                    listener.onComplete();
                }
                progress = 0;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                progress = 0;
                invalidate();
            }
        });
    }

    // রানটাইমে ডাইনামিকালি শ্যাডো চেঞ্জ করার জন্য সেটার মেথডস
    public void setStrokeShadow(float radius, int color) {
        this.strokeShadowRadius = radius;
        this.strokeShadowColor = color;
        // Y offset ৩ রাখা হয়েছে যাতে শ্যাডো সবসময় স্ট্রোকের নিচে ও দুই পাশে ছড়ায়
        arcPaint.setShadowLayer(strokeShadowRadius, 0, 3, strokeShadowColor);
        invalidate();
    }

    public void setButtonColors(int bgColor, int stkColor) {
        this.backgroundColor = bgColor;
        this.strokeColor = stkColor;
        fillPaint.setColor(bgColor);
        arcPaint.setColor(stkColor);
        shadowPaint.setColor(bgColor);
        invalidate();
    }

    public void setStrokeWidth(float widthInDp) {
        this.strokeWidth = dp(widthInDp);
        arcPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setMessage(String msg) {
        message = msg != null ? msg : "";
        invalidate();
    }

    public void setOnCompleteListener(OnCompleteListener l) {
        listener = l;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if (width == 0 || height == 0) return;

        float gap = dp(5);
        float cornerRadius = dp(15);
        // শ্যাডোর রেডিয়াস যত বাড়বে টপ প্যাডিং স্বয়ংক্রিয়ভাবে তত বাড়বে যাতে ভিউ কেটে না যায়
        float basePadding = gap + strokeWidth + dp(strokeShadowRadius + 2);

        float radius = width * 0.8f;
        float chord = width;



        if (radius <= (chord * 0.5f)) {
            radius = (chord * 0.5f) + 1;
        }

        float arcAngle = (float) (Math.asin((chord * 0.5f) / radius) * 2);
        float startAngle = (float) ((Math.PI + Math.PI * 0.5f) - (arcAngle * 0.5f));
        float sweepAngle = arcAngle * progress;

        float cx = width / 2f;
        float curveHeight = radius - (float) (radius * Math.cos(arcAngle / 2f));
        float cy = basePadding + radius;

        rect.set(cx - radius, cy - radius, cx + radius, cy + radius);

        arcRect.set(
                rect.left - gap - strokeWidth / 2f,
                rect.top - gap - strokeWidth / 2f,
                rect.right + gap + strokeWidth / 2f,
                rect.bottom + gap + strokeWidth / 2f
        );

        path.reset();
        path.arcTo(rect, (float) Math.toDegrees(startAngle), (float) Math.toDegrees(arcAngle));

        cornerRect.set(width - (cornerRadius * 2), height - (cornerRadius * 2), width, height);
        path.arcTo(cornerRect, 0, 90, false);

        cornerRect.set(0, height - (cornerRadius * 2), cornerRadius * 2, height);
        path.arcTo(cornerRect, 90, 90, false);

        path.close();

        canvas.drawPath(path, shadowPaint);
        canvas.drawPath(path, fillPaint);

        // কাস্টমাইজড শ্যাডো সহ প্রোগ্রেস লাইন ড্রয়িং
        canvas.drawArc(
                arcRect,
                (float) Math.toDegrees(startAngle),
                (float) Math.toDegrees(sweepAngle),
                false,
                arcPaint
        );

        float centerY = (height + (basePadding + curveHeight)) / 2f;

        canvas.drawText("Tap and hold to confirm", width / 2f, centerY - dp(2), titlePaint);
        canvas.drawText(message, width / 2f, centerY + dp(26), messagePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animator.cancel();
                animator.start();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                animator.cancel();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private float dp(float value) {
        return value * getResources().getDisplayMetrics().density;
    }

    private float sm(float value) {
        return value;
    }

    private float sp(float value) {
        return value * getResources().getDisplayMetrics().scaledDensity;
    }
}