/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.samsung.android.sdk.internal.healthdata.query;

import android.os.Parcel;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import java.io.Serializable;

public class NumberArrayFilter
extends HealthDataResolver.Filter {
    private String a;
    private Number[] b;

    public NumberArrayFilter(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.a = parcel.readString();
        this.b = (Number[])parcel.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.a);
        parcel.writeSerializable((Serializable)this.b);
    }
}

