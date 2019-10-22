/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcxh;

public final class zzcxg
extends zzbfm
implements Result {
    public static final Parcelable.Creator<zzcxg> CREATOR = new zzcxh();
    private int zzeck;
    private int zzkbx;
    private Intent zzkby;

    public zzcxg() {
        this(0, null);
    }

    zzcxg(int n, int n2, Intent intent) {
        this.zzeck = n;
        this.zzkbx = n2;
        this.zzkby = intent;
    }

    private zzcxg(int n, Intent intent) {
        this(2, 0, null);
    }

    @Override
    public final Status getStatus() {
        if (this.zzkbx == 0) {
            return Status.zzfni;
        }
        return Status.zzfnm;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zzc(parcel, 2, this.zzkbx);
        zzbfp.zza(parcel, 3, (Parcelable)this.zzkby, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

