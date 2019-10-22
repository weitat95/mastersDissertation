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
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzap;
import com.google.android.gms.maps.internal.zzg;

public abstract class zzaq
extends zzev
implements zzap {
    public zzaq() {
        this.attachInterface((IInterface)this, "com.google.android.gms.maps.internal.IOnMapReadyCallback");
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
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            if (iInterface instanceof IGoogleMapDelegate) {
                IGoogleMapDelegate iGoogleMapDelegate = (IGoogleMapDelegate)iInterface;
            } else {
                zzg zzg2 = new zzg(iBinder);
            }
        }
        this.zza((IGoogleMapDelegate)var2_5);
        var3_8.writeNoException();
        return true;
    }
}

