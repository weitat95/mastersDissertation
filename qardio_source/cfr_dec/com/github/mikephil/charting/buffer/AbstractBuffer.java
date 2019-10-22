/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.buffer;

public abstract class AbstractBuffer<T> {
    public final float[] buffer;
    protected int index = 0;
    protected int mFrom = 0;
    protected int mTo = 0;
    protected float phaseX = 1.0f;
    protected float phaseY = 1.0f;

    public AbstractBuffer(int n) {
        this.buffer = new float[n];
    }

    public void reset() {
        this.index = 0;
    }

    public void setPhases(float f, float f2) {
        this.phaseX = f;
        this.phaseY = f2;
    }

    public int size() {
        return this.buffer.length;
    }
}

