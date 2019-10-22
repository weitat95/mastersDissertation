/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.memory;

public enum MemoryTrimType {
    OnCloseToDalvikHeapLimit(0.5),
    OnSystemLowMemoryWhileAppInForeground(0.5),
    OnSystemLowMemoryWhileAppInBackground(1.0),
    OnAppBackgrounded(1.0);

    private double mSuggestedTrimRatio;

    private MemoryTrimType(double d) {
        this.mSuggestedTrimRatio = d;
    }

    public double getSuggestedTrimRatio() {
        return this.mSuggestedTrimRatio;
    }
}

