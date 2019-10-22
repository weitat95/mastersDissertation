/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFormatException
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.github.mikephil.charting.data;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import com.github.mikephil.charting.data.BaseEntry;

public class Entry
extends BaseEntry
implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>(){

        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        public Entry[] newArray(int n) {
            return new Entry[n];
        }
    };
    private float x = 0.0f;

    public Entry() {
    }

    public Entry(float f, float f2) {
        super(f2);
        this.x = f;
    }

    protected Entry(Parcel parcel) {
        this.x = parcel.readFloat();
        this.setY(parcel.readFloat());
        if (parcel.readInt() == 1) {
            this.setData((Object)parcel.readParcelable(Object.class.getClassLoader()));
        }
    }

    public int describeContents() {
        return 0;
    }

    public float getX() {
        return this.x;
    }

    public String toString() {
        return "Entry, x: " + this.x + " y: " + this.getY();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.getY());
        if (this.getData() != null) {
            if (this.getData() instanceof Parcelable) {
                parcel.writeInt(1);
                parcel.writeParcelable((Parcelable)this.getData(), n);
                return;
            }
            throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
        }
        parcel.writeInt(0);
    }

}

