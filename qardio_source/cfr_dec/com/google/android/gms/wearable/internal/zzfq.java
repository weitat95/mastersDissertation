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
import com.google.android.gms.wearable.internal.zzay;
import com.google.android.gms.wearable.internal.zzfr;

public final class zzfq
extends zzbfm {
    public static final Parcelable.Creator<zzfq> CREATOR = new zzfr();
    public final int statusCode;
    public final zzay zzlje;

    public zzfq(int n, zzay zzay2) {
        this.statusCode = n;
        this.zzlje = zzay2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlje, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

