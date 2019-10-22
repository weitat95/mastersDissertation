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
import com.google.android.gms.wearable.internal.zzdz;

public final class zzdy
extends zzbfm {
    public static final Parcelable.Creator<zzdy> CREATOR = new zzdz();
    private int statusCode;
    private ConnectionConfiguration[] zzlkl;

    public zzdy(int n, ConnectionConfiguration[] arrconnectionConfiguration) {
        this.statusCode = n;
        this.zzlkl = arrconnectionConfiguration;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza((Parcel)parcel, (int)3, (Parcelable[])this.zzlkl, (int)n, (boolean)false);
        zzbfp.zzai(parcel, n2);
    }
}

