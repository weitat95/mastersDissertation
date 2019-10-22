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
import com.google.android.gms.internal.zzcgm;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcln;

public final class zzcgl
extends zzbfm {
    public static final Parcelable.Creator<zzcgl> CREATOR = new zzcgm();
    public String packageName;
    private int versionCode;
    public String zziyf;
    public zzcln zziyg;
    public long zziyh;
    public boolean zziyi;
    public String zziyj;
    public zzcha zziyk;
    public long zziyl;
    public zzcha zziym;
    public long zziyn;
    public zzcha zziyo;

    zzcgl(int n, String string2, String string3, zzcln zzcln2, long l, boolean bl, String string4, zzcha zzcha2, long l2, zzcha zzcha3, long l3, zzcha zzcha4) {
        this.versionCode = n;
        this.packageName = string2;
        this.zziyf = string3;
        this.zziyg = zzcln2;
        this.zziyh = l;
        this.zziyi = bl;
        this.zziyj = string4;
        this.zziyk = zzcha2;
        this.zziyl = l2;
        this.zziym = zzcha3;
        this.zziyn = l3;
        this.zziyo = zzcha4;
    }

    zzcgl(zzcgl zzcgl2) {
        this.versionCode = 1;
        zzbq.checkNotNull(zzcgl2);
        this.packageName = zzcgl2.packageName;
        this.zziyf = zzcgl2.zziyf;
        this.zziyg = zzcgl2.zziyg;
        this.zziyh = zzcgl2.zziyh;
        this.zziyi = zzcgl2.zziyi;
        this.zziyj = zzcgl2.zziyj;
        this.zziyk = zzcgl2.zziyk;
        this.zziyl = zzcgl2.zziyl;
        this.zziym = zzcgl2.zziym;
        this.zziyn = zzcgl2.zziyn;
        this.zziyo = zzcgl2.zziyo;
    }

    zzcgl(String string2, String string3, zzcln zzcln2, long l, boolean bl, String string4, zzcha zzcha2, long l2, zzcha zzcha3, long l3, zzcha zzcha4) {
        this.versionCode = 1;
        this.packageName = string2;
        this.zziyf = string3;
        this.zziyg = zzcln2;
        this.zziyh = l;
        this.zziyi = bl;
        this.zziyj = string4;
        this.zziyk = zzcha2;
        this.zziyl = l2;
        this.zziym = zzcha3;
        this.zziyn = l3;
        this.zziyo = zzcha4;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.versionCode);
        zzbfp.zza(parcel, 2, this.packageName, false);
        zzbfp.zza(parcel, 3, this.zziyf, false);
        zzbfp.zza(parcel, 4, this.zziyg, n, false);
        zzbfp.zza(parcel, 5, this.zziyh);
        zzbfp.zza(parcel, 6, this.zziyi);
        zzbfp.zza(parcel, 7, this.zziyj, false);
        zzbfp.zza(parcel, 8, this.zziyk, n, false);
        zzbfp.zza(parcel, 9, this.zziyl);
        zzbfp.zza(parcel, 10, this.zziym, n, false);
        zzbfp.zza(parcel, 11, this.zziyn);
        zzbfp.zza(parcel, 12, this.zziyo, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

