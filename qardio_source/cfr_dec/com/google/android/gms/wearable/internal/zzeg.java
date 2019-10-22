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
import com.google.android.gms.wearable.internal.zzeh;
import com.google.android.gms.wearable.internal.zzfo;

public final class zzeg
extends zzbfm {
    public static final Parcelable.Creator<zzeg> CREATOR = new zzeh();
    public final int statusCode;
    public final zzfo zzlko;

    public zzeg(int n, zzfo zzfo2) {
        this.statusCode = n;
        this.zzlko = zzfo2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlko, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

