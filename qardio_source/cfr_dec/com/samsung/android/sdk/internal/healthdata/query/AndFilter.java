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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AndFilter
extends HealthDataResolver.Filter {
    public AndFilter(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public AndFilter(HealthDataResolver.Filter filter, HealthDataResolver.Filter ... arrfilter) {
        AndFilter.checkFilter(filter);
        int n = arrfilter.length;
        for (int i = 0; i < n; ++i) {
            AndFilter.checkFilter(arrfilter[i]);
        }
        this.mType = HealthDataResolver.Filter.ParcelType.AND;
        this.mFilters.add(filter);
        this.mFilters.addAll(Arrays.asList(arrfilter));
    }

    @Override
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.mFilters = parcel.createTypedArrayList(HealthDataResolver.Filter.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeTypedList(this.mFilters);
    }
}

