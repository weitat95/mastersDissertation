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
import java.util.List;

public class NotFilter
extends HealthDataResolver.Filter {
    public NotFilter(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public NotFilter(HealthDataResolver.Filter filter) {
        NotFilter.checkFilter(filter);
        this.mType = HealthDataResolver.Filter.ParcelType.NOT;
        this.mFilters.add(filter);
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

