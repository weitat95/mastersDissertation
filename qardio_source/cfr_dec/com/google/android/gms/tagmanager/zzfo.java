/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.tagmanager.zzbz;
import com.google.android.gms.tagmanager.zzcc;
import com.google.android.gms.tagmanager.zzcd;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzdo;
import com.google.android.gms.tagmanager.zzec;
import com.google.android.gms.tagmanager.zzfn;
import com.google.android.gms.tagmanager.zzfp;
import com.google.android.gms.tagmanager.zzfq;
import com.google.android.gms.tagmanager.zzfr;
import com.google.android.gms.tagmanager.zzfs;

final class zzfo
extends zzfn {
    private static final Object zzkjj = new Object();
    private static zzfo zzkjv;
    private boolean connected = true;
    private Context zzkjk;
    private zzcc zzkjl;
    private volatile zzbz zzkjm;
    private int zzkjn = 1800000;
    private boolean zzkjo = true;
    private boolean zzkjp = false;
    private boolean zzkjq = true;
    private zzcd zzkjr = new zzfp(this);
    private zzfr zzkjs;
    private zzdo zzkjt;
    private boolean zzkju = false;

    private zzfo() {
    }

    private final boolean isPowerSaveMode() {
        return this.zzkju || !this.connected || this.zzkjn <= 0;
    }

    static /* synthetic */ Context zza(zzfo zzfo2) {
        return zzfo2.zzkjk;
    }

    static /* synthetic */ boolean zzb(zzfo zzfo2) {
        return zzfo2.isPowerSaveMode();
    }

    public static zzfo zzbgg() {
        if (zzkjv == null) {
            zzkjv = new zzfo();
        }
        return zzkjv;
    }

    static /* synthetic */ Object zzbgi() {
        return zzkjj;
    }

    static /* synthetic */ int zzc(zzfo zzfo2) {
        return zzfo2.zzkjn;
    }

    static /* synthetic */ boolean zzd(zzfo zzfo2) {
        return zzfo2.connected;
    }

    static /* synthetic */ zzcc zze(zzfo zzfo2) {
        return zzfo2.zzkjl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void dispatch() {
        synchronized (this) {
            if (!this.zzkjp) {
                zzdj.v("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.zzkjo = true;
            } else {
                this.zzkjm.zzl(new zzfq(this));
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zza(Context context, zzbz zzbz2) {
        synchronized (this) {
            Context context2 = this.zzkjk;
            if (context2 == null) {
                this.zzkjk = context.getApplicationContext();
                if (this.zzkjm == null) {
                    void var2_2;
                    this.zzkjm = var2_2;
                }
            }
            return;
        }
    }

    @Override
    public final void zzbgf() {
        synchronized (this) {
            if (!this.isPowerSaveMode()) {
                this.zzkjs.zzbgj();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final zzcc zzbgh() {
        synchronized (this) {
            if (this.zzkjl == null) {
                if (this.zzkjk == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.zzkjl = new zzec(this.zzkjr, this.zzkjk);
            }
            if (this.zzkjs == null) {
                this.zzkjs = new zzfs(this, null);
                if (this.zzkjn > 0) {
                    this.zzkjs.zzs(this.zzkjn);
                }
            }
            this.zzkjp = true;
            if (this.zzkjo) {
                ((zzfn)this).dispatch();
                this.zzkjo = false;
            }
            if (this.zzkjt != null) return this.zzkjl;
            if (!this.zzkjq) return this.zzkjl;
            this.zzkjt = new zzdo(this);
            Object object = this.zzkjt;
            Context context = this.zzkjk;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver((BroadcastReceiver)object, intentFilter);
            intentFilter = new IntentFilter();
            intentFilter.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter.addCategory(context.getPackageName());
            context.registerReceiver((BroadcastReceiver)object, intentFilter);
            return this.zzkjl;
        }
    }

    @Override
    public final void zzbv(boolean bl) {
        synchronized (this) {
            this.zzd(this.zzkju, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzd(boolean bl, boolean bl2) {
        synchronized (this) {
            boolean bl3 = this.isPowerSaveMode();
            this.zzkju = bl;
            this.connected = bl2;
            bl = this.isPowerSaveMode();
            if (bl != bl3) {
                if (this.isPowerSaveMode()) {
                    this.zzkjs.cancel();
                    zzdj.v("PowerSaveMode initiated.");
                } else {
                    this.zzkjs.zzs(this.zzkjn);
                    zzdj.v("PowerSaveMode terminated.");
                }
            }
            return;
        }
    }
}

