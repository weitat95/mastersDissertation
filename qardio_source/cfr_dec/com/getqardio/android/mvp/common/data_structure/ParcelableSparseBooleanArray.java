/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseBooleanArray
 */
package com.getqardio.android.mvp.common.data_structure;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class ParcelableSparseBooleanArray
extends SparseBooleanArray
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseBooleanArray> CREATOR = new Parcelable.Creator<ParcelableSparseBooleanArray>(){

        public ParcelableSparseBooleanArray createFromParcel(Parcel parcel) {
            return new ParcelableSparseBooleanArray(parcel);
        }

        public ParcelableSparseBooleanArray[] newArray(int n) {
            return new ParcelableSparseBooleanArray[n];
        }
    };

    public ParcelableSparseBooleanArray() {
    }

    private ParcelableSparseBooleanArray(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            this.put(parcel.readInt(), ((Boolean)parcel.readValue(null)).booleanValue());
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.size());
        for (n = 0; n < this.size(); ++n) {
            parcel.writeInt(this.keyAt(n));
            parcel.writeValue((Object)this.valueAt(n));
        }
    }

}

