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
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;

final class zzcko
implements Runnable {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcha zzjgs;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ boolean zzjim;
    private /* synthetic */ boolean zzjin;

    zzcko(zzckg zzckg2, boolean bl, boolean bl2, zzcha zzcha2, zzcgi zzcgi2, String string2) {
        this.zzjij = zzckg2;
        this.zzjim = true;
        this.zzjin = bl2;
        this.zzjgs = zzcha2;
        this.zzjgn = zzcgi2;
        this.zzimf = string2;
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
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzjim) {
            zzckg zzckg2 = this.zzjij;
            zzcha zzcha2 = this.zzjin ? null : this.zzjgs;
            zzckg2.zza(zzche2, zzcha2, this.zzjgn);
        } else {
            try {
                if (TextUtils.isEmpty((CharSequence)this.zzimf)) {
                    zzche2.zza(this.zzjgs, this.zzjgn);
                }
                zzche2.zza(this.zzjgs, this.zzimf, ((zzcjk)this.zzjij).zzawy().zzazk());
            }
            catch (RemoteException remoteException) {
                ((zzcjk)this.zzjij).zzawy().zzazd().zzj("Failed to send event to the service", (Object)remoteException);
            }
        }
        zzckg.zze(this.zzjij);
    }
}

