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
import com.google.android.gms.wearable.ConnectionConfiguration;
import com.google.android.gms.wearable.internal.zzdx;

@Deprecated
public final class zzdw
extends zzbfm {
    public static final Parcelable.Creator<zzdw> CREATOR = new zzdx();
    private int statusCode;
    private ConnectionConfiguration zzlkk;

    public zzdw(int n, ConnectionConfiguration connectionConfiguration) {
        this.statusCode = n;
        this.zzlkk = connectionConfiguration;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzlkk, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

