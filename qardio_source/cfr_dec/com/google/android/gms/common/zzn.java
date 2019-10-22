/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.zzh;
import com.google.android.gms.common.zzi;
import com.google.android.gms.common.zzo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzn
extends zzbfm {
    public static final Parcelable.Creator<zzn> CREATOR = new zzo();
    private final String zzflg;
    private final zzh zzflh;
    private final boolean zzfli;

    zzn(String string2, IBinder iBinder, boolean bl) {
        this.zzflg = string2;
        this.zzflh = zzn.zzak(iBinder);
        this.zzfli = bl;
    }

    zzn(String string2, zzh zzh2, boolean bl) {
        this.zzflg = string2;
        this.zzflh = zzh2;
        this.zzfli = bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static zzh zzak(IBinder object) {
        void var0_3;
        void var0_5;
        block5: {
            IObjectWrapper iObjectWrapper;
            block4: {
                if (object == null) {
                    return null;
                }
                try {
                    iObjectWrapper = zzau.zzam((IBinder)object).zzaga();
                    if (iObjectWrapper != null) break block4;
                    Object var0_2 = null;
                    break block5;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate", (Throwable)remoteException);
                    return null;
                }
            }
            byte[] arrby = (byte[])com.google.android.gms.dynamic.zzn.zzx(iObjectWrapper);
        }
        if (var0_3 != null) {
            zzi zzi2 = new zzi((byte[])var0_3);
            return var0_5;
        }
        Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate");
        return var0_5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        IBinder iBinder;
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzflg, false);
        if (this.zzflh == null) {
            Log.w((String)"GoogleCertificatesQuery", (String)"certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzflh.asBinder();
        }
        zzbfp.zza(parcel, 2, iBinder, false);
        zzbfp.zza(parcel, 3, this.zzfli);
        zzbfp.zzai(parcel, n);
    }
}

