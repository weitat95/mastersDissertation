/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbp;
import com.google.android.gms.maps.internal.zzbu;

public abstract class zzbq
extends zzev
implements zzbp {
    public zzbq() {
        this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnStreetViewPanoramaReadyCallback");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        void var4_9;
        void var2_5;
        void var3_8;
        if (this.zza(n, (Parcel)object, (Parcel)var3_8, (int)var4_9)) {
            return true;
        }
        if (n != 1) {
            return false;
        }
        IBinder iBinder = object.readStrongBinder();
        if (iBinder == null) {
            Object var2_4 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            if (iInterface instanceof IStreetViewPanoramaDelegate) {
                IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate = (IStreetViewPanoramaDelegate)iInterface;
            } else {
                zzbu zzbu2 = new zzbu(iBinder);
            }
        }
        this.zza((IStreetViewPanoramaDelegate)var2_5);
        var3_8.writeNoException();
        return true;
    }
}

