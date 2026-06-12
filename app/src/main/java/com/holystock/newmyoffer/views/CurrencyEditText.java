package com.holystock.newmyoffer.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.holystock.newmyoffer.R;

public class CurrencyEditText extends AppCompatEditText {

    private final String prefix = "৳";
    private boolean isUpdating = false;

    public CurrencyEditText(Context context) {
        super(context);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setHint("৳0");

        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._28sp));

        addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if (isUpdating) return;

                isUpdating = true;

                try {

                    String value = s.toString();

                    if (value.isEmpty()) {
                        isUpdating = false;
                        return;
                    }

                    // Remove prefix and commas
                    String number = value
                            .replace(prefix, "")
                            .replace(",", "")
                            .trim();

                    if (number.isEmpty()) {
                        setText("");
                        isUpdating = false;
                        return;
                    }

                    long amount = Long.parseLong(number);

                    String formatted = String.format(
                            java.util.Locale.US,
                            "%,d",
                            amount
                    );

                    setText(prefix + formatted);
                    setSelection(getText().length());

                } catch (Exception ignored) {
                }

                isUpdating = false;
            }
        });

        setBackgroundColor(android.graphics.Color.TRANSPARENT);

        setTextColor(ContextCompat.getColor(getContext(),R.color.primary));

        setHintTextColor(ContextCompat.getColor(getContext(), R.color.grey));

        setGravity(Gravity.CENTER);

        setTypeface(ResourcesCompat.getFont(getContext(), R.font.bangla_medium));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            setTextCursorDrawable(R.drawable.cursor_small);
        }

        setIncludeFontPadding(false);
        setMinLines(1);
        setMaxLines(1);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 🔥 hint show করার জন্য empty রাখা
        if (getText() == null || getText().toString().isEmpty()) {
            setHint("৳0");
        }
    }

    public String getAmount() {

        if (getText() == null) {
            return "";
        }

        return getText()
                .toString()
                .replace(prefix, "")
                .replace(",", "")
                .trim();
    }
}