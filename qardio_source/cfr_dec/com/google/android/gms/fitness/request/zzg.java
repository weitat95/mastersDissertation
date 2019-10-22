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
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzh;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbwt;
import com.google.android.gms.internal.zzbwu;

public final class zzg
extends zzbfm {
    public static final Parcelable.Creator<zzg> CREATOR = new zzh();
    private final int versionCode;
    private DataType zzgzg;
    private final zzbwt zzhgd;
    private final boolean zzhge;

    zzg(int n, IBinder iBinder, DataType dataType, boolean bl) {
        this.versionCode = n;
        this.zzhgd = zzbwu.zzas(iBinder);
        this.zzgzg = dataType;
        this.zzhge = bl;
    }

    public zzg(zzbwt zzbwt2, DataType dataType, boolean bl) {
        this.versionCode = 3;
        this.zzhgd = zzbwt2;
        this.zzgzg = dataType;
        this.zzhge = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        String string2;
        if (this.zzgzg == null) {
            string2 = "null";
            do {
                return String.format("DailyTotalRequest{%s}", string2);
                break;
            } while (true);
        }
        string2 = this.zzgzg.zzaqp();
        return String.format("DailyTotalRequest{%s}", string2);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhgd.asBinder(), false);
        zzbfp.zza(parcel, 2, this.zzgzg, n, false);
        zzbfp.zza(parcel, 4, this.zzhge);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, n2);
    }
}

