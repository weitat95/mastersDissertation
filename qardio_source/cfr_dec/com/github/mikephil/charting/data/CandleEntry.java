/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import com.github.mikephil.charting.data.Entry;

@SuppressLint(value={"ParcelCreator"})
public class CandleEntry
extends Entry {
    private float mClose;
    private float mOpen;
    private float mShadowHigh;
    private float mShadowLow;

    public float getClose() {
        return this.mClose;
    }

    public float getHigh() {
        return this.mShadowHigh;
    }

    public float getLow() {
        return this.mShadowLow;
    }

    public float getOpen() {
        return this.mOpen;
    }

    @Override
    public float getY() {
        return super.getY();
    }
}

