/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.memory;

import com.facebook.imagepipeline.memory.BitmapCounter;

public class BitmapCounterProvider {
    public static final int MAX_BITMAP_TOTAL_SIZE = BitmapCounterProvider.getMaxSizeHardCap();
    private static BitmapCounter sBitmapCounter;

    public static BitmapCounter get() {
        if (sBitmapCounter == null) {
            sBitmapCounter = new BitmapCounter(384, MAX_BITMAP_TOTAL_SIZE);
        }
        return sBitmapCounter;
    }

    private static int getMaxSizeHardCap() {
        int n = (int)Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);
        if ((long)n > 0x1000000L) {
            return n / 4 * 3;
        }
        return n / 2;
    }
}

