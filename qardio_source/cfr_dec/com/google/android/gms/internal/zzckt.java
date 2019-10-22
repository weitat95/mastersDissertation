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
import com.google.android.gms.internal.zzcln;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class zzckt
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ boolean zzjhf;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ AtomicReference zzjik;

    zzckt(zzckg zzckg2, AtomicReference atomicReference, zzcgi zzcgi2, boolean bl) {
        this.zzjij = zzckg2;
        this.zzjik = atomicReference;
        this.zzjgn = zzcgi2;
        this.zzjhf = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        AtomicReference atomicReference = this.zzjik;
        synchronized (atomicReference) {
            try {
                zzche zzche2 = zzckg.zzd(this.zzjij);
                if (zzche2 == null) {
                    ((zzcjk)this.zzjij).zzawy().zzazd().log("Failed to get user properties");
                    return;
                }
                this.zzjik.set(zzche2.zza(this.zzjgn, this.zzjhf));
                zzckg.zze(this.zzjij);
            }
            catch (RemoteException remoteException) {
                ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to get user properties", (Object)remoteException);
            }
            finally {
                this.zzjik.notify();
            }
            return;
        }
    }
}

