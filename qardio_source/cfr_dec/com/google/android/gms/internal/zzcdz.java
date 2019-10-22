/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcdx;
import com.google.android.gms.internal.zzcea;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcew;
import com.google.android.gms.location.zzm;
import com.google.android.gms.location.zzn;

public final class zzcdz
extends zzbfm {
    public static final Parcelable.Creator<zzcdz> CREATOR = new zzcea();
    private int zzikz;
    private zzcdx zzila;
    private zzm zzilb;
    private zzceu zzilc;

    /*
     * Enabled aggressive block sorting
     */
    zzcdz(int n, zzcdx object, IBinder iBinder, IBinder iBinder2) {
        Object var5_5 = null;
        this.zzikz = n;
        this.zzila = object;
        object = iBinder == null ? null : zzn.zzbc(iBinder);
        this.zzilb = object;
        if (iBinder2 == null) {
            object = var5_5;
        } else {
            object = var5_5;
            if (iBinder2 != null) {
                object = iBinder2.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
                object = object instanceof zzceu ? (zzceu)object : new zzcew(iBinder2);
            }
        }
        this.zzilc = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        Object var5_3 = null;
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzikz);
        zzbfp.zza(parcel, 2, this.zzila, n, false);
        Object object = this.zzilb == null ? null : this.zzilb.asBinder();
        zzbfp.zza(parcel, 3, object, false);
        object = this.zzilc == null ? var5_3 : this.zzilc.asBinder();
        zzbfp.zza(parcel, 4, object, false);
        zzbfp.zzai(parcel, n2);
    }
}

