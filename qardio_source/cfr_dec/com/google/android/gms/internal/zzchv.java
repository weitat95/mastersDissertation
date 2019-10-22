/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchq;
import com.google.android.gms.internal.zzchw;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;

class zzchv
extends BroadcastReceiver {
    private static String zzdyg = zzchv.class.getName();
    private boolean mRegistered;
    private boolean zzdyh;
    private final zzcim zziwf;

    zzchv(zzcim zzcim2) {
        zzbq.checkNotNull(zzcim2);
        this.zziwf = zzcim2;
    }

    static /* synthetic */ zzcim zza(zzchv zzchv2) {
        return zzchv2.zziwf;
    }

    public void onReceive(Context object, Intent intent) {
        this.zziwf.zzxf();
        object = intent.getAction();
        this.zziwf.zzawy().zzazj().zzj("NetworkBroadcastReceiver received action", object);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            boolean bl = this.zziwf.zzbab().zzzs();
            if (this.zzdyh != bl) {
                this.zzdyh = bl;
                this.zziwf.zzawx().zzg(new zzchw(this, bl));
            }
            return;
        }
        this.zziwf.zzawy().zzazf().zzj("NetworkBroadcastReceiver received unknown action", object);
    }

    public final void unregister() {
        this.zziwf.zzxf();
        ((zzcjk)this.zziwf.zzawx()).zzve();
        ((zzcjk)this.zziwf.zzawx()).zzve();
        if (!this.mRegistered) {
            return;
        }
        this.zziwf.zzawy().zzazj().log("Unregistering connectivity change receiver");
        this.mRegistered = false;
        this.zzdyh = false;
        Context context = this.zziwf.getContext();
        try {
            context.unregisterReceiver((BroadcastReceiver)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.zziwf.zzawy().zzazd().zzj("Failed to unregister the network broadcast receiver", illegalArgumentException);
            return;
        }
    }

    public final void zzzp() {
        this.zziwf.zzxf();
        ((zzcjk)this.zziwf.zzawx()).zzve();
        if (this.mRegistered) {
            return;
        }
        this.zziwf.getContext().registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.zzdyh = this.zziwf.zzbab().zzzs();
        this.zziwf.zzawy().zzazj().zzj("Registering connectivity change receiver. Network connected", this.zzdyh);
        this.mRegistered = true;
    }
}

