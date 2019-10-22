/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcxr;

public final class zzcxq
extends zzbfm {
    public static final Parcelable.Creator<zzcxq> CREATOR = new zzcxr();
    private int zzeck;
    private final ConnectionResult zzfoo;
    private final zzbt zzkcc;

    public zzcxq(int n) {
        this(new ConnectionResult(8, null), null);
    }

    zzcxq(int n, ConnectionResult connectionResult, zzbt zzbt2) {
        this.zzeck = n;
        this.zzfoo = connectionResult;
        this.zzkcc = zzbt2;
    }

    private zzcxq(ConnectionResult connectionResult, zzbt zzbt2) {
        this(1, connectionResult, null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, this.zzfoo, n, false);
        zzbfp.zza(parcel, 3, this.zzkcc, n, false);
        zzbfp.zzai(parcel, n2);
    }

    public final ConnectionResult zzahf() {
        return this.zzfoo;
    }

    public final zzbt zzbdi() {
        return this.zzkcc;
    }
}

