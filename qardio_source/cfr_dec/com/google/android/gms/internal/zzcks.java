/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcln;

final class zzcks
implements Runnable {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcln zzjgt;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ boolean zzjin;

    zzcks(zzckg zzckg2, boolean bl, zzcln zzcln2, zzcgi zzcgi2) {
        this.zzjij = zzckg2;
        this.zzjin = bl;
        this.zzjgt = zzcln2;
        this.zzjgn = zzcgi2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        zzche zzche2 = zzckg.zzd(this.zzjij);
        if (zzche2 == null) {
            ((zzcjk)this.zzjij).zzawy().zzazd().log("Discarding data. Failed to set user attribute");
            return;
        }
        zzckg zzckg2 = this.zzjij;
        zzcln zzcln2 = this.zzjin ? null : this.zzjgt;
        zzckg2.zza(zzche2, zzcln2, this.zzjgn);
        zzckg.zze(this.zzjij);
    }
}

