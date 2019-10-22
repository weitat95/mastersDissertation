/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzb;
import com.google.android.gms.tagmanager.zzc;
import com.google.android.gms.tagmanager.zzd;
import com.google.android.gms.tagmanager.zzdj;

public final class zza {
    private static Object zzkcr = new Object();
    private static zza zzkcs;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private final com.google.android.gms.common.util.zzd zzata;
    private final Thread zzdac;
    private volatile AdvertisingIdClient.Info zzdsn;
    private volatile long zzkcl = 900000L;
    private volatile long zzkcm = 30000L;
    private volatile long zzkcn;
    private volatile long zzkco;
    private final Object zzkcp = new Object();
    private zzd zzkcq = new zzb(this);

    private zza(Context context) {
        this(context, null, zzh.zzamg());
    }

    /*
     * Enabled aggressive block sorting
     */
    private zza(Context context, zzd zzd2, com.google.android.gms.common.util.zzd zzd3) {
        this.zzata = zzd3;
        this.mContext = context != null ? context.getApplicationContext() : context;
        this.zzkcn = this.zzata.currentTimeMillis();
        this.zzdac = new Thread(new zzc(this));
    }

    static /* synthetic */ Context zza(zza zza2) {
        return zza2.mContext;
    }

    static /* synthetic */ void zzb(zza zza2) {
        zza2.zzbdn();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbdk() {
        synchronized (this) {
            try {
                if (!this.mClosed) {
                    this.zzbdl();
                    this.wait(500L);
                }
            }
            catch (InterruptedException interruptedException) {}
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbdl() {
        if (this.zzata.currentTimeMillis() - this.zzkcn > this.zzkcm) {
            Object object = this.zzkcp;
            synchronized (object) {
                this.zzkcp.notify();
            }
            this.zzkcn = this.zzata.currentTimeMillis();
        }
    }

    private final void zzbdm() {
        if (this.zzata.currentTimeMillis() - this.zzkco > 3600000L) {
            this.zzdsn = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbdn() {
        Process.setThreadPriority((int)10);
        while (!this.mClosed) {
            Object object = this.zzkcq.zzbdo();
            if (object != null) {
                this.zzdsn = object;
                this.zzkco = this.zzata.currentTimeMillis();
                zzdj.zzct("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                this.notifyAll();
            }
            try {
                object = this.zzkcp;
                synchronized (object) {
                }
            }
            catch (InterruptedException interruptedException) {
                zzdj.zzct("sleep interrupted in AdvertiserDataPoller thread; continuing");
                continue;
            }
            {
                this.zzkcp.wait(this.zzkcl);
            }
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zza zzeb(Context object) {
        if (zzkcs == null) {
            Object object2 = zzkcr;
            synchronized (object2) {
                if (zzkcs == null) {
                    object = new zza((Context)object);
                    zzkcs = object;
                    object.zzdac.start();
                }
            }
        }
        return zzkcs;
    }

    public final void close() {
        this.mClosed = true;
        this.zzdac.interrupt();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean isLimitAdTrackingEnabled() {
        if (this.zzdsn == null) {
            this.zzbdk();
        } else {
            this.zzbdl();
        }
        this.zzbdm();
        if (this.zzdsn == null) {
            return true;
        }
        return this.zzdsn.isLimitAdTrackingEnabled();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String zzbdj() {
        if (this.zzdsn == null) {
            this.zzbdk();
        } else {
            this.zzbdl();
        }
        this.zzbdm();
        if (this.zzdsn == null) {
            return null;
        }
        return this.zzdsn.getId();
    }
}

