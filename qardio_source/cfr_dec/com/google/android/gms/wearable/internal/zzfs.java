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
import com.google.android.gms.wearable.internal.zzft;

public final class zzfs
extends zzbfm {
    public static final Parcelable.Creator<zzfs> CREATOR = new zzft();
    private String label;
    private String packageName;
    private long zzllb;

    public zzfs(String string2, String string3, long l) {
        this.packageName = string2;
        this.label = string3;
        this.zzllb = l;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.packageName, false);
        zzbfp.zza(parcel, 3, this.label, false);
        zzbfp.zza(parcel, 4, this.zzllb);
        zzbfp.zzai(parcel, n);
    }
}

