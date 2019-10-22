/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package com.github.mikephil.charting.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.List;

public class BarDataSet
extends BarLineScatterCandleBubbleDataSet<BarEntry>
implements IBarDataSet {
    private int mBarBorderColor = -16777216;
    private float mBarBorderWidth = 0.0f;
    private int mBarShadowColor = Color.rgb((int)215, (int)215, (int)215);
    private int mEntryCountStacks = 0;
    private int mHighLightAlpha = 120;
    private String[] mStackLabels = new String[]{"Stack"};
    private int mStackSize = 1;

    public BarDataSet(List<BarEntry> list, String string2) {
        super(list, string2);
        this.mHighLightColor = Color.rgb((int)0, (int)0, (int)0);
        this.calcStackSize(list);
        this.calcEntryCountIncludingStacks(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calcEntryCountIncludingStacks(List<BarEntry> list) {
        this.mEntryCountStacks = 0;
        int n = 0;
        while (n < list.size()) {
            float[] arrf = list.get(n).getYVals();
            this.mEntryCountStacks = arrf == null ? ++this.mEntryCountStacks : (this.mEntryCountStacks += arrf.length);
            ++n;
        }
        return;
    }

    private void calcStackSize(List<BarEntry> list) {
        for (int i = 0; i < list.size(); ++i) {
            float[] arrf = list.get(i).getYVals();
            if (arrf == null || arrf.length <= this.mStackSize) continue;
            this.mStackSize = arrf.length;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void calcMinMax(BarEntry barEntry) {
        if (barEntry != null && !Float.isNaN(barEntry.getY())) {
            if (barEntry.getYVals() == null) {
                if (barEntry.getY() < this.mYMin) {
                    this.mYMin = barEntry.getY();
                }
                if (barEntry.getY() > this.mYMax) {
                    this.mYMax = barEntry.getY();
                }
            } else {
                if (-barEntry.getNegativeSum() < this.mYMin) {
                    this.mYMin = -barEntry.getNegativeSum();
                }
                if (barEntry.getPositiveSum() > this.mYMax) {
                    this.mYMax = barEntry.getPositiveSum();
                }
            }
            this.calcMinMaxX(barEntry);
        }
    }

    @Override
    public int getBarBorderColor() {
        return this.mBarBorderColor;
    }

    @Override
    public float getBarBorderWidth() {
        return this.mBarBorderWidth;
    }

    @Override
    public int getBarShadowColor() {
        return this.mBarShadowColor;
    }

    @Override
    public int getHighLightAlpha() {
        return this.mHighLightAlpha;
    }

    @Override
    public String[] getStackLabels() {
        return this.mStackLabels;
    }

    @Override
    public int getStackSize() {
        return this.mStackSize;
    }

    @Override
    public boolean isStacked() {
        return this.mStackSize > 1;
    }
}

