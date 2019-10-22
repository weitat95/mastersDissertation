/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Range;

@SuppressLint(value={"ParcelCreator"})
public class BarEntry
extends Entry {
    private float mNegativeSum;
    private float mPositiveSum;
    private Range[] mRanges;
    private float[] mYVals;

    public BarEntry(float f, float f2) {
        super(f, f2);
    }

    public float getNegativeSum() {
        return this.mNegativeSum;
    }

    public float getPositiveSum() {
        return this.mPositiveSum;
    }

    public Range[] getRanges() {
        return this.mRanges;
    }

    @Override
    public float getY() {
        return super.getY();
    }

    public float[] getYVals() {
        return this.mYVals;
    }

    public boolean isStacked() {
        return this.mYVals != null;
    }
}

