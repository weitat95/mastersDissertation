/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzaa;

public final class zzz
extends zzbfm {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();
    private final String zzeuy;
    private final String zzijw;
    private final String zzijx;
    private final int zzijy;
    private final boolean zzijz;

    zzz(String string2, String string3, String string4, int n, boolean bl) {
        this.zzeuy = string2;
        this.zzijw = string3;
        this.zzijx = string4;
        this.zzijy = n;
        this.zzijz = bl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzijw, false);
        zzbfp.zza(parcel, 2, this.zzijx, false);
        zzbfp.zzc(parcel, 3, this.zzijy);
        zzbfp.zza(parcel, 4, this.zzijz);
        zzbfp.zza(parcel, 5, this.zzeuy, false);
        zzbfp.zzai(parcel, n);
    }
}

