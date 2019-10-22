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
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcep;

public final class zzceo
extends zzbfm
implements Result {
    public static final Parcelable.Creator<zzceo> CREATOR;
    private static zzceo zzilj;
    private final Status mStatus;

    static {
        zzilj = new zzceo(Status.zzfni);
        CREATOR = new zzcep();
    }

    public zzceo(Status status) {
        this.mStatus = status;
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getStatus(), n, false);
        zzbfp.zzai(parcel, n2);
    }
}

