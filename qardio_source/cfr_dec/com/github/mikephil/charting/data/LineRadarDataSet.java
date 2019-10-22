/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;

public abstract class LineRadarDataSet<T extends Entry>
extends LineScatterCandleRadarDataSet<T>
implements ILineRadarDataSet<T> {
    private boolean mDrawFilled;
    private int mFillAlpha;
    private int mFillColor;
    protected Drawable mFillDrawable;
    private float mLineWidth;

    @Override
    public int getFillAlpha() {
        return this.mFillAlpha;
    }

    @Override
    public int getFillColor() {
        return this.mFillColor;
    }

    @Override
    public Drawable getFillDrawable() {
        return this.mFillDrawable;
    }

    @Override
    public float getLineWidth() {
        return this.mLineWidth;
    }

    @Override
    public boolean isDrawFilledEnabled() {
        return this.mDrawFilled;
    }
}

