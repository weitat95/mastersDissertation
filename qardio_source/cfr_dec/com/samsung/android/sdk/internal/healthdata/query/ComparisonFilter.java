/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.internal.healthdata.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import java.io.Serializable;

public class ComparisonFilter
extends HealthDataResolver.Filter {
    private Operator a;
    private String b;
    private Number c;

    public ComparisonFilter(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public ComparisonFilter(Operator operator, String string2, Number number) {
        this.mType = HealthDataResolver.Filter.ParcelType.COMPARABLE;
        this.a = operator;
        this.b = string2;
        this.c = number;
    }

    @Override
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.a = (Operator)parcel.readParcelable(Operator.class.getClassLoader());
        this.b = parcel.readString();
        this.c = (Number)parcel.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable((Parcelable)this.a, 0);
        parcel.writeString(this.b);
        parcel.writeSerializable((Serializable)this.c);
    }

    public static enum Operator implements Parcelable
    {
        GREATER_THAN_EQUALSjava.lang.IllegalArgumentException: fromIndex(2) > toIndex(1)

