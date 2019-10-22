/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;

public class LimitLine
extends ComponentBase {
    private DashPathEffect mDashPathEffect;
    private String mLabel;
    private LimitLabelPosition mLabelPosition;
    private float mLimit;
    private int mLineColor;
    private float mLineWidth;
    private Paint.Style mTextStyle;

    public DashPathEffect getDashPathEffect() {
        return this.mDashPathEffect;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public LimitLabelPosition getLabelPosition() {
        return this.mLabelPosition;
    }

    public float getLimit() {
        return this.mLimit;
    }

    public int getLineColor() {
        return this.mLineColor;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public Paint.Style getTextStyle() {
        return this.mTextStyle;
    }

    public static enum LimitLabelPosition {
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM;

    }

}

