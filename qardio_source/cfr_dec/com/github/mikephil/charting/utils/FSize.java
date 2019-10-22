/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;

public final class FSize
extends ObjectPool.Poolable {
    private static ObjectPool<FSize> pool = ObjectPool.create(256, new FSize(0.0f, 0.0f));
    public float height;
    public float width;

    static {
        pool.setReplenishPercentage(0.5f);
    }

    public FSize() {
    }

    public FSize(float f, float f2) {
        this.width = f;
        this.height = f2;
    }

    public static FSize getInstance(float f, float f2) {
        FSize fSize = pool.get();
        fSize.width = f;
        fSize.height = f2;
        return fSize;
    }

    public static void recycleInstance(FSize fSize) {
        pool.recycle(fSize);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof FSize)) return false;
        object = (FSize)object;
        if (this.width != ((FSize)object).width) return false;
        if (this.height != ((FSize)object).height) return false;
        return bl;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.width) ^ Float.floatToIntBits(this.height);
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new FSize(0.0f, 0.0f);
    }

    public String toString() {
        return this.width + "x" + this.height;
    }
}

