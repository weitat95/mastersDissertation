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
import com.google.android.gms.internal.zzdkt;

public final class zzdks
extends zzbfm {
    public static final Parcelable.Creator<zzdks> CREATOR = new zzdkt();
    private byte[] zzleg;

    zzdks() {
        this(new byte[0]);
    }

    public zzdks(byte[] arrby) {
        this.zzleg = arrby;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzleg, false);
        zzbfp.zzai(parcel, n);
    }
}

