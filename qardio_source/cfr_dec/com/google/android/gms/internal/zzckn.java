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

final class zzckn
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzckg zzjij;

    zzckn(zzckg zzckg2, zzcgi zzcgi2) {
        this.zzjij = zzckg2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final void run() {
        zzche zzche2 = zzckg.zzd(this.zzjij);
        if (zzche2 == null) {
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzche2.zzb(this.zzjgn);
            zzckg.zze(this.zzjij);
            return;
        }
        catch (RemoteException remoteException) {
            ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to send measurementEnabled to the service", (Object)remoteException);
            return;
        }
    }
}

