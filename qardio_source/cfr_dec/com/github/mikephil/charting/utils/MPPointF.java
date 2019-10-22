/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.github.mikephil.charting.utils;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.mikephil.charting.utils.ObjectPool;

public class MPPointF
extends ObjectPool.Poolable {
    public static final Parcelable.Creator<MPPointF> CREATOR;
    private static ObjectPool<MPPointF> pool;
    public float x;
    public float y;

    static {
        pool = ObjectPool.create(32, new MPPointF(0.0f, 0.0f));
        pool.setReplenishPercentage(0.5f);
        CREATOR = new Parcelable.Creator<MPPointF>(){

            public MPPointF createFromParcel(Parcel parcel) {
                MPPointF mPPointF = new MPPointF(0.0f, 0.0f);
                mPPointF.my_readFromParcel(parcel);
                return mPPointF;
            }

            public MPPointF[] newArray(int n) {
                return new MPPointF[n];
            }
        };
    }

    public MPPointF() {
    }

    public MPPointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public static MPPointF getInstance(float f, float f2) {
        MPPointF mPPointF = pool.get();
        mPPointF.x = f;
        mPPointF.y = f2;
        return mPPointF;
    }

    public static void recycleInstance(MPPointF mPPointF) {
        pool.recycle(mPPointF);
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new MPPointF(0.0f, 0.0f);
    }

    public void my_readFromParcel(Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
    }

}

