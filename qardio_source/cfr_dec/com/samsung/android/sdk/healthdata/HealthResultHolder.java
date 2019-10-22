/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.healthdata;

import android.os.Parcel;
import android.os.Parcelable;

public interface HealthResultHolder<T extends BaseResult> {
    public T await();

    public void cancel();

    public void setResultListener(ResultListener<T> var1);

    public static class BaseResult
    implements Parcelable {
        public static final Parcelable.Creator<BaseResult> CREATOR = new Parcelable.Creator<BaseResult>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new BaseResult(parcel);
            }
        };
        protected final boolean mCached;
        protected final int mCount;
        protected final int mStatus;

        public BaseResult(int n, int n2) {
            this.mStatus = n;
            this.mCount = n2;
            this.mCached = true;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected BaseResult(Parcel parcel) {
            boolean bl = true;
            this.mStatus = parcel.readInt();
            this.mCount = parcel.readInt();
            if (parcel.readInt() != 1) {
                bl = false;
            }
            this.mCached = bl;
        }

        public int describeContents() {
            return 0;
        }

        public int getCount() {
            return this.mCount;
        }

        public int getStatus() {
            return this.mStatus;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mStatus);
            parcel.writeInt(this.mCount);
            n = this.mCached ? 1 : 0;
            parcel.writeInt(n);
        }

    }

    public static interface ResultListener<T extends BaseResult> {
        public void onResult(T var1);
    }

}

