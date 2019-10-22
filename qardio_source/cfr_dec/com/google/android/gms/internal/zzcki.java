/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;

final class zzcki
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzckg zzjij;

    zzcki(zzckg zzckg2, zzcgi zzcgi2) {
        this.zzjij = zzckg2;
        this.zzjgn = zzcgi2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        zzche zzche2 = zzckg.zzd(this.zzjij);
        if (zzche2 == null) {
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzche2.zzd(this.zzjgn);
        }
        catch (RemoteException remoteException) {
            ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to reset data on the service", (Object)remoteException);
        }
        zzckg.zze(this.zzjij);
    }
}

