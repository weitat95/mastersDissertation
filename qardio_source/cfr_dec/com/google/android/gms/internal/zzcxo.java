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
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcxp;

public final class zzcxo
extends zzbfm {
    public static final Parcelable.Creator<zzcxo> CREATOR = new zzcxp();
    private int zzeck;
    private zzbr zzkcb;

    zzcxo(int n, zzbr zzbr2) {
        this.zzeck = n;
        this.zzkcb = zzbr2;
    }

    public zzcxo(zzbr zzbr2) {
        this(1, zzbr2);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, this.zzkcb, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

