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
import com.google.android.gms.wearable.internal.zzfs;
import com.google.android.gms.wearable.internal.zzgf;
import java.util.List;

public final class zzge
extends zzbfm {
    public static final Parcelable.Creator<zzge> CREATOR = new zzgf();
    private int statusCode;
    private long zzllb;
    private List<zzfs> zzlld;

    public zzge(int n, long l, List<zzfs> list) {
        this.statusCode = n;
        this.zzllb = l;
        this.zzlld = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zza(parcel, 3, this.zzllb);
        zzbfp.zzc(parcel, 4, this.zzlld, false);
        zzbfp.zzai(parcel, n);
    }
}

