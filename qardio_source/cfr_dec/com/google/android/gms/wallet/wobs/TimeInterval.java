/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.wobs.zzk;

public final class TimeInterval
extends zzbfm {
    public static final Parcelable.Creator<TimeInterval> CREATOR = new zzk();
    private long zzlge;
    private long zzlgf;

    TimeInterval() {
    }

    public TimeInterval(long l, long l2) {
        this.zzlge = l;
        this.zzlgf = l2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlge);
        zzbfp.zza(parcel, 3, this.zzlgf);
        zzbfp.zzai(parcel, n);
    }
}

