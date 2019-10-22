/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;

public class MPPointD
extends ObjectPool.Poolable {
    private static ObjectPool<MPPointD> pool = ObjectPool.create(64, new MPPointD(0.0, 0.0));
    public double x;
    public double y;

    static {
        pool.setReplenishPercentage(0.5f);
    }

    private MPPointD(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public static MPPointD getInstance(double d, double d2) {
        MPPointD mPPointD = pool.get();
        mPPointD.x = d;
        mPPointD.y = d2;
        return mPPointD;
    }

    public static void recycleInstance(MPPointD mPPointD) {
        pool.recycle(mPPointD);
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new MPPointD(0.0, 0.0);
    }

    public String toString() {
        return "MPPointD, x: " + this.x + ", y: " + this.y;
    }
}

