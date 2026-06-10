package com.holystock.newmyoffer.controller;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.holystock.newmyoffer.model.TextSegment;

import java.util.List;

public class RichTextBuilder {

    public static void apply(
            TextView textView,
            List<TextSegment> segments
    ) {

        SpannableStringBuilder builder =
                new SpannableStringBuilder();

        boolean hasClickable = false;

        for (TextSegment segment : segments) {

            int start = builder.length();

            builder.append(segment.getText());

            int end = builder.length();

            if (segment.getTextColor() != null) {

                builder.setSpan(
                        new ForegroundColorSpan(
                                segment.getTextColor()
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getBackgroundColor() != null) {

                builder.setSpan(
                        new BackgroundColorSpan(
                                segment.getBackgroundColor()
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            builder.setSpan(
                    new StyleSpan(
                            segment.getStyle()
                    ),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            if (segment.isUnderline()) {

                builder.setSpan(
                        new UnderlineSpan(),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.isStrikeThrough()) {

                builder.setSpan(
                        new StrikethroughSpan(),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.isSuperscript()) {

                builder.setSpan(
                        new SuperscriptSpan(),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.isSubscript()) {

                builder.setSpan(
                        new SubscriptSpan(),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getRelativeSize() > 0) {

                builder.setSpan(
                        new RelativeSizeSpan(
                                segment.getRelativeSize()
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getTextSize() > 0) {

                builder.setSpan(
                        new AbsoluteSizeSpan(
                                (int) segment.getTextSize(),
                                true
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getTypefaceFamily() != null) {

                builder.setSpan(
                        new TypefaceSpan(
                                segment.getTypefaceFamily()
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getUrl() != null) {

                builder.setSpan(
                        new URLSpan(
                                segment.getUrl()
                        ),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }

            if (segment.getClickListener() != null) {

                hasClickable = true;

                builder.setSpan(
                        new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                segment.getClickListener().onClick(widget);
                            }
                        },
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        textView.setText(builder);

        if (hasClickable) {
            textView.setMovementMethod(
                    LinkMovementMethod.getInstance()
            );
            textView.setHighlightColor(0);
        }
    }
}