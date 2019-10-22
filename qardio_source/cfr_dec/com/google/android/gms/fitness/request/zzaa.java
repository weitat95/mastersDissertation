/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.request.zzab;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzaa
extends zzbfm {
    public static final Parcelable.Creator<zzaa> CREATOR = new zzab();
    private final int zzeck;
    private final zzbyf zzhgc;

    zzaa(int n, IBinder iBinder) {
        this.zzeck = n;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzaa(zzbyf zzbyf2) {
        this.zzeck = 2;
        this.zzhgc = zzbyf2;
    }

    public final String toString() {
        return String.format("DisableFitRequest", new Object[0]);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n);
    }
}

