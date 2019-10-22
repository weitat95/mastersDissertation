/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.components;

import android.graphics.Typeface;

public abstract class ComponentBase {
    protected boolean mEnabled = true;
    protected int mTextColor = -16777216;
    protected float mTextSize = 10.0f;
    protected Typeface mTypeface = null;
    protected float mXOffset = 5.0f;
    protected float mYOffset = 5.0f;

    public int getTextColor() {
        return this.mTextColor;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public float getXOffset() {
        return this.mXOffset;
    }

    public float getYOffset() {
        return this.mYOffset;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean bl) {
        this.mEnabled = bl;
    }
}

