package com.holystock.newmyoffer.data.model;

import android.graphics.Typeface;
import android.view.View;

public class TextSegment {

    private final String text;

    private Integer textColor;
    private Integer backgroundColor;

    private int style = Typeface.NORMAL;

    private boolean underline;
    private boolean strikeThrough;

    private boolean superscript;
    private boolean subscript;

    private float relativeSize = -1;
    private float textSize = -1;

    private String typefaceFamily;

    private String url;

    private View.OnClickListener clickListener;

    public TextSegment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public TextSegment setTextColor(Integer textColor) {
        this.textColor = textColor;
        return this;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public TextSegment setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getStyle() {
        return style;
    }

    public TextSegment setTextStyle(int style) {
        this.style = style;
        return this;
    }

    public boolean isUnderline() {
        return underline;
    }

    public TextSegment setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public TextSegment setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
        return this;
    }

    public boolean isSuperscript() {
        return superscript;
    }

    public TextSegment setSuperscript(boolean superscript) {
        this.superscript = superscript;
        return this;
    }

    public boolean isSubscript() {
        return subscript;
    }

    public TextSegment setSubscript(boolean subscript) {
        this.subscript = subscript;
        return this;
    }

    public float getRelativeSize() {
        return relativeSize;
    }

    public TextSegment setRelativeSize(float relativeSize) {
        this.relativeSize = relativeSize;
        return this;
    }

    public float getTextSize() {
        return textSize;
    }

    public TextSegment setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public String getTypefaceFamily() {
        return typefaceFamily;
    }

    public TextSegment setTypefaceFamily(
            String typefaceFamily
    ) {
        this.typefaceFamily = typefaceFamily;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TextSegment setUrl(String url) {
        this.url = url;
        return this;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public TextSegment setClickListener(
            View.OnClickListener clickListener
    ) {
        this.clickListener = clickListener;
        return this;
    }
}