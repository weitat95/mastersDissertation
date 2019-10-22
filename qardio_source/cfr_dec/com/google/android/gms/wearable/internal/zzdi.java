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
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzdj;
import java.util.List;

public final class zzdi
extends zzbfm {
    public static final Parcelable.Creator<zzdi> CREATOR = new zzdj();
    public final int statusCode;
    public final List<zzah> zzlke;

    public zzdi(int n, List<zzah> list) {
        this.statusCode = n;
        this.zzlke = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zzc(parcel, 3, this.zzlke, false);
        zzbfp.zzai(parcel, n);
    }
}

