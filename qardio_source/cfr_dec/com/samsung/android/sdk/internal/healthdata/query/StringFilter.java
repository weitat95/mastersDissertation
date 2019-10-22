/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.samsung.android.sdk.internal.healthdata.query;

import android.os.Parcel;
import com.samsung.android.sdk.healthdata.HealthDataResolver;

public class StringFilter
extends HealthDataResolver.Filter {
    private String a;
    private String b;

    public StringFilter(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public StringFilter(String string2, String string3) {
        this.mType = HealthDataResolver.Filter.ParcelType.STRING;
        this.a = string2;
        this.b = string3;
    }

    @Override
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.a = parcel.readString();
        this.b = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.a);
        parcel.writeString(this.b);
    }
}

