/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;

final class zzckk
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzckg zzjij;

    zzckk(zzckg zzckg2, zzcgi zzcgi2) {
        this.zzjij = zzckg2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final void run() {
        zzche zzche2 = zzckg.zzd(this.zzjij);
        if (zzche2 == null) {
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzche2.zza(this.zzjgn);
            this.zzjij.zza(zzche2, null, this.zzjgn);
            zzckg.zze(this.zzjij);
            return;
        }
        catch (RemoteException remoteException) {
            ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to send app launch to the service", (Object)remoteException);
            return;
        }
    }
}

