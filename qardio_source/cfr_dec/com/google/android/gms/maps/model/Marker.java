/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.zzp;

public final class Marker {
    private final zzp zzivf;

    public Marker(zzp zzp2) {
        this.zzivf = zzbq.checkNotNull(zzp2);
    }

    public final boolean equals(Object object) {
        if (!(object instanceof Marker)) {
            return false;
        }
        try {
            boolean bl = this.zzivf.zzj(((Marker)object).zzivf);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public final int hashCode() {
        try {
            int n = this.zzivf.hashCodeRemote();
            return n;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }
}

