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
import com.google.android.gms.wearable.internal.zzds;

public final class zzdt
extends zzbfm {
    public static final Parcelable.Creator<zzdt> CREATOR = new zzds();
    private int statusCode;
    private boolean zzlki;
    private boolean zzlkj;

    public zzdt(int n, boolean bl, boolean bl2) {
        this.statusCode = n;
        this.zzlki = bl;
        this.zzlkj = bl2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlki);
        zzbfp.zza(parcel, 4, this.zzlkj);
        zzbfp.zzai(parcel, n);
    }
}

