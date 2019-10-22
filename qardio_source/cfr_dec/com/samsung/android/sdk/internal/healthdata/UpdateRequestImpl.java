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

public final class UpdateRequestImpl
implements Parcelable,
HealthDataResolver.UpdateRequest {
    public static final Parcelable.Creator<UpdateRequestImpl> CREATOR = new Parcelable.Creator<UpdateRequestImpl>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UpdateRequestImpl(parcel);
        }
    };
    private final String a;
    private final HealthData b;
    private final HealthDataResolver.Filter c;
    private List<String> d = null;

    public UpdateRequestImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (HealthData)parcel.readParcelable(HealthData.class.getClassLoader());
        this.c = (HealthDataResolver.Filter)parcel.readParcelable(HealthDataResolver.Filter.class.getClassLoader());
        this.d = new ArrayList<String>();
        parcel.readStringList(this.d);
    }

    public UpdateRequestImpl(String string2, HealthData healthData, HealthDataResolver.Filter filter, List<String> list) {
        this.a = string2;
        this.b = healthData;
        this.c = filter;
        this.d = list;
    }

    public final int describeContents() {
        return 0;
    }

    public final HealthData getDataObject() {
        return this.b;
    }

    public final String getDataType() {
        return this.a;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeParcelable((Parcelable)this.b, 0);
        parcel.writeParcelable((Parcelable)this.c, 0);
        parcel.writeStringList(this.d);
    }

}

