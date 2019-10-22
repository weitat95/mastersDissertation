/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcfc;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcfn
extends zzcfc {
    private zzn<LocationSettingsResult> zzilv;

    /*
     * Enabled aggressive block sorting
     */
    public zzcfn(zzn<LocationSettingsResult> zzn2) {
        boolean bl = zzn2 != null;
        zzbq.checkArgument(bl, "listener can't be null.");
        this.zzilv = zzn2;
    }

    @Override
    public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
        this.zzilv.setResult(locationSettingsResult);
        this.zzilv = null;
    }
}

