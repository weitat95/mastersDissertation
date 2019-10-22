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
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzdkr;

public final class zzdkq
extends zzbfm {
    public static final Parcelable.Creator<zzdkq> CREATOR = new zzdkr();
    private byte[] zzlef;

    zzdkq() {
        this(new byte[0]);
    }

    public zzdkq(byte[] arrby) {
        this.zzlef = arrby;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlef, false);
        zzbfp.zzai(parcel, n);
    }
}

