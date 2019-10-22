/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.zza;

public final class MapsInitializer {
    private static boolean initialized = false;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int initialize(Context object) {
        int n = 0;
        synchronized (MapsInitializer.class) {
            zzbq.checkNotNull(object, "Context is null");
            boolean bl = initialized;
            if (bl) return n;
            try {
                object = zzbz.zzdt((Context)object);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                return googlePlayServicesNotAvailableException.errorCode;
            }
            try {}
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
            CameraUpdateFactory.zza(object.zzawc());
            BitmapDescriptorFactory.zza(object.zzawd());
            initialized = true;
            return n;
        }
    }
}

