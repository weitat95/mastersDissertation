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
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzbk;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzbj
extends zzbfm {
    public static final Parcelable.Creator<zzbj> CREATOR = new zzbk();
    private final int zzeck;
    private final zzbyf zzhgc;
    private Subscription zzhif;
    private final boolean zzhig;

    zzbj(int n, Subscription subscription, boolean bl, IBinder iBinder) {
        this.zzeck = n;
        this.zzhif = subscription;
        this.zzhig = bl;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzbj(Subscription subscription, boolean bl, zzbyf zzbyf2) {
        this.zzeck = 3;
        this.zzhif = subscription;
        this.zzhig = false;
        this.zzhgc = zzbyf2;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("subscription", this.zzhif).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhif, n, false);
        zzbfp.zza(parcel, 2, this.zzhig);
        IBinder iBinder = this.zzhgc == null ? null : this.zzhgc.asBinder();
        zzbfp.zza(parcel, 3, iBinder, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}

