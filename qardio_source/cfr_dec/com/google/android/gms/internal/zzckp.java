/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;

final class zzckp
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ boolean zzjim;
    private /* synthetic */ boolean zzjin;
    private /* synthetic */ zzcgl zzjio;
    private /* synthetic */ zzcgl zzjip;

    zzckp(zzckg zzckg2, boolean bl, boolean bl2, zzcgl zzcgl2, zzcgi zzcgi2, zzcgl zzcgl3) {
        this.zzjij = zzckg2;
        this.zzjim = true;
        this.zzjin = bl2;
        this.zzjio = zzcgl2;
        this.zzjgn = zzcgi2;
        this.zzjip = zzcgl3;
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
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzjim) {
            zzckg zzckg2 = this.zzjij;
            zzcgl zzcgl2 = this.zzjin ? null : this.zzjio;
            zzckg2.zza(zzche2, zzcgl2, this.zzjgn);
        } else {
            try {
                if (TextUtils.isEmpty((CharSequence)this.zzjip.packageName)) {
                    zzche2.zza(this.zzjio, this.zzjgn);
                }
                zzche2.zzb(this.zzjio);
            }
            catch (RemoteException remoteException) {
                ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to send conditional user property to the service", (Object)remoteException);
            }
        }
        zzckg.zze(this.zzjij);
    }
}

