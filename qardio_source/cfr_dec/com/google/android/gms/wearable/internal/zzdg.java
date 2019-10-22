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
import com.google.android.gms.wearable.internal.zzdh;

public final class zzdg
extends zzbfm {
    public static final Parcelable.Creator<zzdg> CREATOR = new zzdh();
    public final int statusCode;
    public final int zzlkd;

    public zzdg(int n, int n2) {
        this.statusCode = n;
        this.zzlkd = n2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zzc(parcel, 3, this.zzlkd);
        zzbfp.zzai(parcel, n);
    }
}

