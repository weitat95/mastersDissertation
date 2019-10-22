/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.internal.zzef;

public final class zzee
extends zzbfm {
    public static final Parcelable.Creator<zzee> CREATOR = new zzef();
    public final int statusCode;
    public final ParcelFileDescriptor zzjnk;

    public zzee(int n, ParcelFileDescriptor parcelFileDescriptor) {
        this.statusCode = n;
        this.zzjnk = parcelFileDescriptor;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, (Parcelable)this.zzjnk, n | 1, false);
        zzbfp.zzai(parcel, n2);
    }
}

