/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.internal.healthdata;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import java.util.ArrayList;
import java.util.List;

public final class InsertRequestImpl
implements Parcelable,
HealthDataResolver.InsertRequest {
    public static final Parcelable.Creator<InsertRequestImpl> CREATOR = new Parcelable.Creator<InsertRequestImpl>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new InsertRequestImpl(parcel, 0);
        }
    };
    private final String a;
    private final List<HealthData> b;

    private InsertRequestImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.createTypedArrayList(HealthData.CREATOR);
    }

    /* synthetic */ InsertRequestImpl(Parcel parcel, byte by) {
        this(parcel);
    }

    public InsertRequestImpl(String string2) {
        this.a = string2;
        this.b = new ArrayList<HealthData>();
    }

    private static void a(HealthData healthData) {
        if (healthData == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (healthData.getSourceDevice() == null) {
            throw new IllegalArgumentException("source device is not set");
        }
    }

    @Override
    public final void addHealthData(HealthData healthData) {
        InsertRequestImpl.a(healthData);
        this.b.add(healthData);
    }

    public final int describeContents() {
        return 0;
    }

    public final String getDataType() {
        return this.a;
    }

    public final List<HealthData> getItems() {
        return this.b;
    }

    public final boolean isEmpty() {
        return this.b.isEmpty();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeTypedList(this.b);
    }

}

