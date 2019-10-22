/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 */
package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class YAxis
extends AxisBase {
    private AxisDependency mAxisDependency;
    private boolean mDrawTopYLabelEntry = true;
    protected boolean mDrawZeroLine = false;
    protected boolean mInverted = false;
    protected float mMaxWidth = Float.POSITIVE_INFINITY;
    protected float mMinWidth = 0.0f;
    private YAxisLabelPosition mPosition = YAxisLabelPosition.OUTSIDE_CHART;
    protected float mSpacePercentBottom = 10.0f;
    protected float mSpacePercentTop = 10.0f;
    protected int mZeroLineColor = -7829368;
    protected float mZeroLineWidth = 1.0f;

    public YAxis() {
        this.mAxisDependency = AxisDependency.LEFT;
        this.mYOffset = 0.0f;
    }

    public YAxis(AxisDependency axisDependency) {
        this.mAxisDependency = axisDependency;
        this.mYOffset = 0.0f;
    }

    @Override
    public void calculate(float f, float f2) {
        if (this.mCustomAxisMin) {
            f = this.mAxisMinimum;
        }
        if (this.mCustomAxisMax) {
            f2 = this.mAxisMaximum;
        }
        float f3 = Math.abs(f2 - f);
        float f4 = f2;
        float f5 = f;
        if (f3 == 0.0f) {
            f4 = f2 + 1.0f;
            f5 = f - 1.0f;
        }
        if (!this.mCustomAxisMin) {
            this.mAxisMinimum = f5 - f3 / 100.0f * this.getSpaceBottom();
        }
        if (!this.mCustomAxisMax) {
            this.mAxisMaximum = f4 + f3 / 100.0f * this.getSpaceTop();
        }
        this.mAxisRange = Math.abs(this.mAxisMaximum - this.mAxisMinimum);
    }

    public AxisDependency getAxisDependency() {
        return this.mAxisDependency;
    }

    public YAxisLabelPosition getLabelPosition() {
        return this.mPosition;
    }

    public float getMaxWidth() {
        return this.mMaxWidth;
    }

    public float getMinWidth() {
        return this.mMinWidth;
    }

    public float getRequiredHeightSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        return (float)Utils.calcTextHeight(paint, this.getLongestLabel()) + this.getYOffset() * 2.0f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public float getRequiredWidthSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        float f = (float)Utils.calcTextWidth(paint, this.getLongestLabel()) + this.getXOffset() * 2.0f;
        float f2 = this.getMinWidth();
        float f3 = this.getMaxWidth();
        float f4 = f2;
        if (f2 > 0.0f) {
            f4 = Utils.convertDpToPixel(f2);
        }
        f2 = f3;
        if (f3 > 0.0f) {
            f2 = f3;
            if (f3 != Float.POSITIVE_INFINITY) {
                f2 = Utils.convertDpToPixel(f3);
            }
        }
        if ((double)f2 > 0.0) {
            do {
                return Math.max(f4, Math.min(f, f2));
                break;
            } while (true);
        }
        f2 = f;
        return Math.max(f4, Math.min(f, f2));
    }

    public float getSpaceBottom() {
        return this.mSpacePercentBottom;
    }

    public float getSpaceTop() {
        return this.mSpacePercentTop;
    }

    public int getZeroLineColor() {
        return this.mZeroLineColor;
    }

    public float getZeroLineWidth() {
        return this.mZeroLineWidth;
    }

    public boolean isDrawTopYLabelEntryEnabled() {
        return this.mDrawTopYLabelEntry;
    }

    public boolean isDrawZeroLineEnabled() {
        return this.mDrawZeroLine;
    }

    public boolean isInverted() {
        return this.mInverted;
    }

    public boolean needsOffset() {
        return this.isEnabled() && this.isDrawLabelsEnabled() && this.getLabelPosition() == YAxisLabelPosition.OUTSIDE_CHART;
    }

    public void setDrawZeroLine(boolean bl) {
        this.mDrawZeroLine = bl;
    }

    public static enum AxisDependency {
        LEFT,
        RIGHT;

    }

    public static enum YAxisLabelPosition {
        OUTSIDE_CHART,
        INSIDE_CHART;

    }

}

