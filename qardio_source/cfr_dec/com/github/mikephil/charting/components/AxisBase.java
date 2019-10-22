/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 */
package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class AxisBase
extends ComponentBase {
    private int mAxisLineColor = -7829368;
    private DashPathEffect mAxisLineDashPathEffect = null;
    private float mAxisLineWidth = 1.0f;
    public float mAxisMaximum = 0.0f;
    public float mAxisMinimum = 0.0f;
    public float mAxisRange = 0.0f;
    protected IAxisValueFormatter mAxisValueFormatter;
    protected boolean mCenterAxisLabels = false;
    public float[] mCenteredEntries;
    protected boolean mCustomAxisMax = false;
    protected boolean mCustomAxisMin = false;
    public int mDecimals;
    protected boolean mDrawAxisLine = true;
    protected boolean mDrawGridLines = true;
    protected boolean mDrawLabels = true;
    protected boolean mDrawLimitLineBehindData = false;
    public float[] mEntries = new float[0];
    public int mEntryCount;
    protected boolean mForceLabels = false;
    protected float mGranularity = 1.0f;
    protected boolean mGranularityEnabled = false;
    private int mGridColor = -7829368;
    private DashPathEffect mGridDashPathEffect = null;
    private float mGridLineWidth = 1.0f;
    private int mLabelCount = 6;
    protected List<LimitLine> mLimitLines;
    protected float mSpaceMax = 0.0f;
    protected float mSpaceMin = 0.0f;

    public AxisBase() {
        this.mCenteredEntries = new float[0];
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList<LimitLine>();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void calculate(float f, float f2) {
        f = this.mCustomAxisMin ? this.mAxisMinimum : (f -= this.mSpaceMin);
        f2 = this.mCustomAxisMax ? this.mAxisMaximum : (f2 += this.mSpaceMax);
        float f3 = f2;
        float f4 = f;
        if (Math.abs(f2 - f) == 0.0f) {
            f3 = f2 + 1.0f;
            f4 = f - 1.0f;
        }
        this.mAxisMinimum = f4;
        this.mAxisMaximum = f3;
        this.mAxisRange = Math.abs(f3 - f4);
    }

    public int getAxisLineColor() {
        return this.mAxisLineColor;
    }

    public DashPathEffect getAxisLineDashPathEffect() {
        return this.mAxisLineDashPathEffect;
    }

    public float getAxisLineWidth() {
        return this.mAxisLineWidth;
    }

    public String getFormattedLabel(int n) {
        if (n < 0 || n >= this.mEntries.length) {
            return "";
        }
        return this.getValueFormatter().getFormattedValue(this.mEntries[n], this);
    }

    public float getGranularity() {
        return this.mGranularity;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public DashPathEffect getGridDashPathEffect() {
        return this.mGridDashPathEffect;
    }

    public float getGridLineWidth() {
        return this.mGridLineWidth;
    }

    public int getLabelCount() {
        return this.mLabelCount;
    }

    public List<LimitLine> getLimitLines() {
        return this.mLimitLines;
    }

    public String getLongestLabel() {
        String string2 = "";
        for (int i = 0; i < this.mEntries.length; ++i) {
            String string3 = this.getFormattedLabel(i);
            String string4 = string2;
            if (string3 != null) {
                string4 = string2;
                if (string2.length() < string3.length()) {
                    string4 = string3;
                }
            }
            string2 = string4;
        }
        return string2;
    }

    public IAxisValueFormatter getValueFormatter() {
        if (this.mAxisValueFormatter == null || this.mAxisValueFormatter instanceof DefaultAxisValueFormatter && ((DefaultAxisValueFormatter)this.mAxisValueFormatter).getDecimalDigits() != this.mDecimals) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        }
        return this.mAxisValueFormatter;
    }

    public boolean isCenterAxisLabelsEnabled() {
        return this.mCenterAxisLabels && this.mEntryCount > 0;
    }

    public boolean isDrawAxisLineEnabled() {
        return this.mDrawAxisLine;
    }

    public boolean isDrawGridLinesEnabled() {
        return this.mDrawGridLines;
    }

    public boolean isDrawLabelsEnabled() {
        return this.mDrawLabels;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return this.mDrawLimitLineBehindData;
    }

    public boolean isForceLabelsEnabled() {
        return this.mForceLabels;
    }

    public boolean isGranularityEnabled() {
        return this.mGranularityEnabled;
    }

    public void setAxisMinimum(float f) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = f;
        this.mAxisRange = Math.abs(this.mAxisMaximum - f);
    }

    public void setDrawAxisLine(boolean bl) {
        this.mDrawAxisLine = bl;
    }

    public void setDrawGridLines(boolean bl) {
        this.mDrawGridLines = bl;
    }

    public void setDrawLabels(boolean bl) {
        this.mDrawLabels = bl;
    }

    public void setLabelCount(int n) {
        int n2 = n;
        if (n > 25) {
            n2 = 25;
        }
        n = n2;
        if (n2 < 2) {
            n = 2;
        }
        this.mLabelCount = n;
        this.mForceLabels = false;
    }

    public void setSpaceMax(float f) {
        this.mSpaceMax = f;
    }

    public void setSpaceMin(float f) {
        this.mSpaceMin = f;
    }

    public void setValueFormatter(IAxisValueFormatter iAxisValueFormatter) {
        if (iAxisValueFormatter == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
            return;
        }
        this.mAxisValueFormatter = iAxisValueFormatter;
    }
}

