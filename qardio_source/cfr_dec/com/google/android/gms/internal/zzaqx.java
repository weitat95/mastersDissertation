/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzaqy;

public final class zzaqx
implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<zzaqx> CREATOR = new zzaqy();
    private String mValue;
    private String zzbuz;
    private String zzdvg;

    @Deprecated
    public zzaqx() {
    }

    @Deprecated
    zzaqx(Parcel parcel) {
        this.zzbuz = parcel.readString();
        this.zzdvg = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Deprecated
    public final int describeContents() {
        return 0;
    }

    public final String getId() {
        return this.zzbuz;
    }

    public final String getValue() {
        return this.mValue;
    }

    @Deprecated
    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.zzbuz);
        parcel.writeString(this.zzdvg);
        parcel.writeString(this.mValue);
    }
}

