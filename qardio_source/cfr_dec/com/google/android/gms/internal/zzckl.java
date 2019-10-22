/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.measurement.AppMeasurement;

final class zzckl
implements Runnable {
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ AppMeasurement.zzb zzjil;

    zzckl(zzckg zzckg2, AppMeasurement.zzb zzb2) {
        this.zzjij = zzckg2;
        this.zzjil = zzb2;
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
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzjil == null) {
                zzche2.zza(0L, null, null, ((zzcjk)this.zzjij).getContext().getPackageName());
            } else {
                zzche2.zza(this.zzjil.zziwm, this.zzjil.zziwk, this.zzjil.zziwl, ((zzcjk)this.zzjij).getContext().getPackageName());
            }
            zzckg.zze(this.zzjij);
            return;
        }
        catch (RemoteException remoteException) {
            ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to send current screen to the service", (Object)remoteException);
            return;
        }
    }
}

