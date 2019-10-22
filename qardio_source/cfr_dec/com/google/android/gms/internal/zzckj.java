/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcic;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckg;
import java.util.concurrent.atomic.AtomicReference;

final class zzckj
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ AtomicReference zzjik;

    zzckj(zzckg zzckg2, AtomicReference atomicReference, zzcgi zzcgi2) {
        this.zzjij = zzckg2;
        this.zzjik = atomicReference;
        this.zzjgn = zzcgi2;
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
                Object object = zzckg.zzd(this.zzjij);
                if (object == null) {
                    ((zzcjk)this.zzjij).zzawy().zzazd().log("Failed to get app instance id");
                    return;
                }
                this.zzjik.set(object.zzc(this.zzjgn));
                object = (String)this.zzjik.get();
                if (object != null) {
                    ((zzcjk)this.zzjij).zzawm().zzjp((String)object);
                    this.zzjij.zzawz().zzjcx.zzjq((String)object);
                }
                zzckg.zze(this.zzjij);
            }
            catch (RemoteException remoteException) {
                ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to get app instance id", (Object)remoteException);
            }
            finally {
                this.zzjik.notify();
            }
            return;
        }
    }
}

