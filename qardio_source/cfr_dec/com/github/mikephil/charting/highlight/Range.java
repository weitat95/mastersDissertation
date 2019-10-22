/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

public final class Range {
    public float from;
    public float to;

    public boolean contains(float f) {
        return f > this.from && f <= this.to;
    }
}

