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
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import java.util.ArrayList;
import java.util.List;

public final class DeleteRequestImpl
implements Parcelable,
HealthDataResolver.DeleteRequest {
    public static final Parcelable.Creator<DeleteRequestImpl> CREATOR = new Parcelable.Creator<DeleteRequestImpl>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new DeleteRequestImpl(parcel, 0);
        }
    };
    private final String a;
    private final HealthDataResolver.Filter b;
    private List<String> c = null;

    private DeleteRequestImpl(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (HealthDataResolver.Filter)parcel.readParcelable(HealthDataResolver.Filter.class.getClassLoader());
        this.c = new ArrayList<String>();
        parcel.readStringList(this.c);
    }

    /* synthetic */ DeleteRequestImpl(Parcel parcel, byte by) {
        this(parcel);
    }

    public DeleteRequestImpl(String string2, HealthDataResolver.Filter filter, List<String> list) {
        this.a = string2;
        this.b = filter;
        this.c = list;
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeParcelable((Parcelable)this.b, 0);
        parcel.writeStringList(this.c);
    }

}

