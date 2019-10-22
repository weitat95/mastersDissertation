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
import com.google.android.gms.wearable.internal.zzeb;
import com.google.android.gms.wearable.internal.zzfo;
import java.util.List;

public final class zzea
extends zzbfm {
    public static final Parcelable.Creator<zzea> CREATOR = new zzeb();
    public final int statusCode;
    public final List<zzfo> zzlkm;

    public zzea(int n, List<zzfo> list) {
        this.statusCode = n;
        this.zzlkm = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.statusCode);
        zzbfp.zzc(parcel, 3, this.zzlkm, false);
        zzbfp.zzai(parcel, n);
    }
}

