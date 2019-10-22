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
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzchb;

public final class zzcha
extends zzbfm {
    public static final Parcelable.Creator<zzcha> CREATOR = new zzchb();
    public final String name;
    public final String zziyf;
    public final zzcgx zzizt;
    public final long zzizu;

    zzcha(zzcha zzcha2, long l) {
        zzbq.checkNotNull(zzcha2);
        this.name = zzcha2.name;
        this.zzizt = zzcha2.zzizt;
        this.zziyf = zzcha2.zziyf;
        this.zzizu = l;
    }

    public zzcha(String string2, zzcgx zzcgx2, String string3, long l) {
        this.name = string2;
        this.zzizt = zzcgx2;
        this.zziyf = string3;
        this.zzizu = l;
    }

    public final String toString() {
        String string2 = this.zziyf;
        String string3 = this.name;
        String string4 = String.valueOf(this.zzizt);
        return new StringBuilder(String.valueOf(string2).length() + 21 + String.valueOf(string3).length() + String.valueOf(string4).length()).append("origin=").append(string2).append(",name=").append(string3).append(",params=").append(string4).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.name, false);
        zzbfp.zza(parcel, 3, this.zzizt, n, false);
        zzbfp.zza(parcel, 4, this.zziyf, false);
        zzbfp.zza(parcel, 5, this.zzizu);
        zzbfp.zzai(parcel, n2);
    }
}

