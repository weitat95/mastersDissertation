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
import com.google.android.gms.wearable.internal.zzdu;

public final class zzdv
extends zzbfm {
    public static final Parcelable.Creator<zzdv> CREATOR = new zzdu();
    private boolean enabled;
    private int statusCode;

    public zzdv(int n, boolean bl) {
        this.statusCode = n;
        this.enabled = bl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.enabled);
        zzbfp.zzai(parcel, n);
    }
}

