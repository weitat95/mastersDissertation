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
import com.google.android.gms.fitness.request.zzak;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbxw;
import com.google.android.gms.internal.zzbxx;

public final class zzaj
extends zzbfm {
    public static final Parcelable.Creator<zzaj> CREATOR = new zzak();
    private final int zzeck;
    private final DataType zzhbs;
    private final zzbxw zzhhf;

    zzaj(int n, DataType dataType, IBinder iBinder) {
        this.zzeck = n;
        this.zzhbs = dataType;
        this.zzhhf = zzbxx.zzax(iBinder);
    }

    public zzaj(DataType dataType, zzbxw zzbxw2) {
        this.zzeck = 3;
        this.zzhbs = dataType;
        this.zzhhf = zzbxw2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhbs, n, false);
        IBinder iBinder = this.zzhhf == null ? null : this.zzhhf.asBinder();
        zzbfp.zza(parcel, 2, iBinder, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}

