/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.widget.RemoteViews
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzdkv;

public final class zzdku
extends zzbfm {
    public static final Parcelable.Creator<zzdku> CREATOR = new zzdkv();
    private String[] zzleh;
    private int[] zzlei;
    private RemoteViews zzlej;
    private byte[] zzlek;

    private zzdku() {
    }

    public zzdku(String[] arrstring, int[] arrn, RemoteViews remoteViews, byte[] arrby) {
        this.zzleh = arrstring;
        this.zzlei = arrn;
        this.zzlej = remoteViews;
        this.zzlek = arrby;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzleh, false);
        zzbfp.zza(parcel, 2, this.zzlei, false);
        zzbfp.zza(parcel, 3, (Parcelable)this.zzlej, n, false);
        zzbfp.zza(parcel, 4, this.zzlek, false);
        zzbfp.zzai(parcel, n2);
    }
}

