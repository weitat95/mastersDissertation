/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcgj;

public final class zzcgi
extends zzbfm {
    public static final Parcelable.Creator<zzcgi> CREATOR = new zzcgj();
    public final String packageName;
    public final String zzifm;
    public final String zzixs;
    public final String zzixt;
    public final long zzixu;
    public final long zzixv;
    public final String zzixw;
    public final boolean zzixx;
    public final boolean zzixy;
    public final long zzixz;
    public final String zziya;
    public final long zziyb;
    public final long zziyc;
    public final int zziyd;
    public final boolean zziye;

    zzcgi(String string2, String string3, String string4, long l, String string5, long l2, long l3, String string6, boolean bl, boolean bl2, String string7, long l4, long l5, int n, boolean bl3) {
        zzbq.zzgm(string2);
        this.packageName = string2;
        string2 = string3;
        if (TextUtils.isEmpty((CharSequence)string3)) {
            string2 = null;
        }
        this.zzixs = string2;
        this.zzifm = string4;
        this.zzixz = l;
        this.zzixt = string5;
        this.zzixu = l2;
        this.zzixv = l3;
        this.zzixw = string6;
        this.zzixx = bl;
        this.zzixy = bl2;
        this.zziya = string7;
        this.zziyb = l4;
        this.zziyc = l5;
        this.zziyd = n;
        this.zziye = bl3;
    }

    zzcgi(String string2, String string3, String string4, String string5, long l, long l2, String string6, boolean bl, boolean bl2, long l3, String string7, long l4, long l5, int n, boolean bl3) {
        this.packageName = string2;
        this.zzixs = string3;
        this.zzifm = string4;
        this.zzixz = l3;
        this.zzixt = string5;
        this.zzixu = l;
        this.zzixv = l2;
        this.zzixw = string6;
        this.zzixx = bl;
        this.zzixy = bl2;
        this.zziya = string7;
        this.zziyb = l4;
        this.zziyc = l5;
        this.zziyd = n;
        this.zziye = bl3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.packageName, false);
        zzbfp.zza(parcel, 3, this.zzixs, false);
        zzbfp.zza(parcel, 4, this.zzifm, false);
        zzbfp.zza(parcel, 5, this.zzixt, false);
        zzbfp.zza(parcel, 6, this.zzixu);
        zzbfp.zza(parcel, 7, this.zzixv);
        zzbfp.zza(parcel, 8, this.zzixw, false);
        zzbfp.zza(parcel, 9, this.zzixx);
        zzbfp.zza(parcel, 10, this.zzixy);
        zzbfp.zza(parcel, 11, this.zzixz);
        zzbfp.zza(parcel, 12, this.zziya, false);
        zzbfp.zza(parcel, 13, this.zziyb);
        zzbfp.zza(parcel, 14, this.zziyc);
        zzbfp.zzc(parcel, 15, this.zziyd);
        zzbfp.zza(parcel, 16, this.zziye);
        zzbfp.zzai(parcel, n);
    }
}

