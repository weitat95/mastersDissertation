/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.zza;

public final class BitmapDescriptorFactory {
    private static zza zziud;

    public static BitmapDescriptor fromResource(int n) {
        try {
            BitmapDescriptor bitmapDescriptor = new BitmapDescriptor(BitmapDescriptorFactory.zzawe().zzea(n));
            return bitmapDescriptor;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public static void zza(zza zza2) {
        if (zziud != null) {
            return;
        }
        zziud = zzbq.checkNotNull(zza2);
    }

    private static zza zzawe() {
        return zzbq.checkNotNull(zziud, "IBitmapDescriptorFactory is not initialized");
    }
}

