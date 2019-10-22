/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

public abstract class BaseEntry {
    private Object mData = null;
    private float y = 0.0f;

    public BaseEntry() {
    }

    public BaseEntry(float f) {
        this.y = f;
    }

    public Object getData() {
        return this.mData;
    }

    public float getY() {
        return this.y;
    }

    public void setData(Object object) {
        this.mData = object;
    }

    public void setY(float f) {
        this.y = f;
    }
}

