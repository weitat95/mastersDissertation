/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.zzb;

@Deprecated
public final class zza
extends zzbfm {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private String name;
    private String phoneNumber;
    private String zzctp;
    private String zziec;
    private String zzied;
    private String zziee;
    private String zziej;
    private boolean zziel;
    private String zziem;
    private String zzkyr;
    private String zzkys;

    zza() {
    }

    zza(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, boolean bl, String string11) {
        this.name = string2;
        this.zziec = string3;
        this.zzied = string4;
        this.zziee = string5;
        this.zzctp = string6;
        this.zzkyr = string7;
        this.zzkys = string8;
        this.zziej = string9;
        this.phoneNumber = string10;
        this.zziel = bl;
        this.zziem = string11;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.name, false);
        zzbfp.zza(parcel, 3, this.zziec, false);
        zzbfp.zza(parcel, 4, this.zzied, false);
        zzbfp.zza(parcel, 5, this.zziee, false);
        zzbfp.zza(parcel, 6, this.zzctp, false);
        zzbfp.zza(parcel, 7, this.zzkyr, false);
        zzbfp.zza(parcel, 8, this.zzkys, false);
        zzbfp.zza(parcel, 9, this.zziej, false);
        zzbfp.zza(parcel, 10, this.phoneNumber, false);
        zzbfp.zza(parcel, 11, this.zziel);
        zzbfp.zza(parcel, 12, this.zziem, false);
        zzbfp.zzai(parcel, n);
    }
}

