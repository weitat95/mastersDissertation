/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.zzd;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzc
extends zzbfm {
    public static final Parcelable.Creator<zzc> CREATOR = new zzd();
    private String name;
    private int version;

    public zzc(String string2, int n) {
        this.name = string2;
        this.version = n;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.name, false);
        zzbfp.zzc(parcel, 2, this.version);
        zzbfp.zzai(parcel, n);
    }
}

