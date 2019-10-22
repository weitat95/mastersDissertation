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
import com.google.android.gms.wearable.internal.zzdd;
import com.google.android.gms.wearable.internal.zzed;

public final class zzec
extends zzbfm {
    public static final Parcelable.Creator<zzec> CREATOR = new zzed();
    public final int statusCode;
    public final zzdd zzlkn;

    public zzec(int n, zzdd zzdd2) {
        this.statusCode = n;
        this.zzlkn = zzdd2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlkn, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

