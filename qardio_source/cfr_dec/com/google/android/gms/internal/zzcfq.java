/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcew;
import com.google.android.gms.internal.zzcfo;
import com.google.android.gms.internal.zzcfr;
import com.google.android.gms.location.zzp;
import com.google.android.gms.location.zzq;
import com.google.android.gms.location.zzs;
import com.google.android.gms.location.zzt;

public final class zzcfq
extends zzbfm {
    public static final Parcelable.Creator<zzcfq> CREATOR = new zzcfr();
    private PendingIntent zzeeo;
    private int zzikz;
    private zzceu zzilc;
    private zzcfo zzima;
    private zzs zzimb;
    private zzp zzimc;

    /*
     * Enabled aggressive block sorting
     */
    zzcfq(int n, zzcfo object, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        Object var7_7 = null;
        this.zzikz = n;
        this.zzima = object;
        object = iBinder == null ? null : zzt.zzbe(iBinder);
        this.zzimb = object;
        this.zzeeo = pendingIntent;
        object = iBinder2 == null ? null : zzq.zzbd(iBinder2);
        this.zzimc = object;
        if (iBinder3 == null) {
            object = var7_7;
        } else {
            object = var7_7;
            if (iBinder3 != null) {
                object = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
                object = object instanceof zzceu ? (zzceu)object : new zzcew(iBinder3);
            }
        }
        this.zzilc = object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static zzcfq zza(zzp zzp2, zzceu zzceu2) {
        IBinder iBinder = zzp2.asBinder();
        if (zzceu2 != null) {
            zzp2 = zzceu2.asBinder();
            do {
                return new zzcfq(2, null, null, null, iBinder, (IBinder)zzp2);
                break;
            } while (true);
        }
        zzp2 = null;
        return new zzcfq(2, null, null, null, iBinder, (IBinder)zzp2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static zzcfq zza(zzs zzs2, zzceu zzceu2) {
        IBinder iBinder = zzs2.asBinder();
        if (zzceu2 != null) {
            zzs2 = zzceu2.asBinder();
            do {
                return new zzcfq(2, null, iBinder, null, null, (IBinder)zzs2);
                break;
            } while (true);
        }
        zzs2 = null;
        return new zzcfq(2, null, iBinder, null, null, (IBinder)zzs2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        Object var5_3 = null;
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzikz);
        zzbfp.zza(parcel, 2, this.zzima, n, false);
        Object object = this.zzimb == null ? null : this.zzimb.asBinder();
        zzbfp.zza(parcel, 3, object, false);
        zzbfp.zza(parcel, 4, (Parcelable)this.zzeeo, n, false);
        object = this.zzimc == null ? null : this.zzimc.asBinder();
        zzbfp.zza(parcel, 5, object, false);
        object = this.zzilc == null ? var5_3 : this.zzilc.asBinder();
        zzbfp.zza(parcel, 6, object, false);
        zzbfp.zzai(parcel, n2);
    }
}

