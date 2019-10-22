/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzdl;

public final class zzdk
extends zzbfm {
    public static final Parcelable.Creator<zzdk> CREATOR = new zzdl();
    public final int statusCode;
    public final zzah zzlkf;

    public zzdk(int n, zzah zzah2) {
        this.statusCode = n;
        this.zzlkf = zzah2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlkf, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

