/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 */
package com.github.mikephil.charting.data;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;

public abstract class LineScatterCandleRadarDataSet<T extends Entry>
extends BarLineScatterCandleBubbleDataSet<T>
implements ILineScatterCandleRadarDataSet<T> {
    protected boolean mDrawHorizontalHighlightIndicator;
    protected boolean mDrawVerticalHighlightIndicator;
    protected DashPathEffect mHighlightDashPathEffect;
    protected float mHighlightLineWidth;

    @Override
    public DashPathEffect getDashPathEffectHighlight() {
        return this.mHighlightDashPathEffect;
    }

    @Override
    public float getHighlightLineWidth() {
        return this.mHighlightLineWidth;
    }

    @Override
    public boolean isHorizontalHighlightIndicatorEnabled() {
        return this.mDrawHorizontalHighlightIndicator;
    }

    @Override
    public boolean isVerticalHighlightIndicatorEnabled() {
        return this.mDrawVerticalHighlightIndicator;
    }
}

