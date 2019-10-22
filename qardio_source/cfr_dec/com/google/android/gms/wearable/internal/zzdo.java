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
import com.google.android.gms.wearable.internal.zzdp;

public final class zzdo
extends zzbfm {
    public static final Parcelable.Creator<zzdo> CREATOR = new zzdp();
    public final int statusCode;
    public final ParcelFileDescriptor zzlkg;

    public zzdo(int n, ParcelFileDescriptor parcelFileDescriptor) {
        this.statusCode = n;
        this.zzlkg = parcelFileDescriptor;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, (Parcelable)this.zzlkg, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

