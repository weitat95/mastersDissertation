/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzchz;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclg;
import com.google.android.gms.internal.zzclh;
import com.google.android.gms.internal.zzclq;

public final class zzclf
extends zzcjl {
    private Handler mHandler;
    private long zzjjc;
    private final zzcgs zzjjd;
    private final zzcgs zzjje;

    zzclf(zzcim zzcim2) {
        super(zzcim2);
        this.zzjjd = new zzclg(this, this.zziwf);
        this.zzjje = new zzclh(this, this.zziwf);
        this.zzjjc = ((zzcjk)this).zzws().elapsedRealtime();
    }

    static /* synthetic */ void zza(zzclf zzclf2) {
        zzclf2.zzbaw();
    }

    static /* synthetic */ void zza(zzclf zzclf2, long l) {
        zzclf2.zzbe(l);
    }

    static /* synthetic */ void zzb(zzclf zzclf2, long l) {
        zzclf2.zzbf(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbav() {
        synchronized (this) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper());
            }
            return;
        }
    }

    private final void zzbaw() {
        ((zzcjk)this).zzve();
        this.zzbs(false);
        ((zzcjk)this).zzawk().zzaj(((zzcjk)this).zzws().elapsedRealtime());
    }

    private final void zzbe(long l) {
        ((zzcjk)this).zzve();
        this.zzbav();
        this.zzjjd.cancel();
        this.zzjje.cancel();
        ((zzcjk)this).zzawy().zzazj().zzj("Activity resumed, time", l);
        this.zzjjc = l;
        if (((zzcjk)this).zzws().currentTimeMillis() - this.zzawz().zzjdf.get() > this.zzawz().zzjdh.get()) {
            this.zzawz().zzjdg.set(true);
            this.zzawz().zzjdi.set(0L);
        }
        if (this.zzawz().zzjdg.get()) {
            this.zzjjd.zzs(Math.max(0L, this.zzawz().zzjde.get() - this.zzawz().zzjdi.get()));
            return;
        }
        this.zzjje.zzs(Math.max(0L, 3600000L - this.zzawz().zzjdi.get()));
    }

    private final void zzbf(long l) {
        ((zzcjk)this).zzve();
        this.zzbav();
        this.zzjjd.cancel();
        this.zzjje.cancel();
        ((zzcjk)this).zzawy().zzazj().zzj("Activity paused, time", l);
        if (this.zzjjc != 0L) {
            this.zzawz().zzjdi.set(this.zzawz().zzjdi.get() + (l - this.zzjjc));
        }
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final boolean zzbs(boolean bl) {
        ((zzcjk)this).zzve();
        this.zzxf();
        long l = ((zzcjk)this).zzws().elapsedRealtime();
        this.zzawz().zzjdh.set(((zzcjk)this).zzws().currentTimeMillis());
        long l2 = l - this.zzjjc;
        if (!bl && l2 < 1000L) {
            ((zzcjk)this).zzawy().zzazj().zzj("Screen exposed for less than 1000 ms. Event not sent. time", l2);
            return false;
        }
        this.zzawz().zzjdi.set(l2);
        ((zzcjk)this).zzawy().zzazj().zzj("Recording user engagement, ms", l2);
        Bundle bundle = new Bundle();
        bundle.putLong("_et", l2);
        zzckc.zza(((zzcjk)this).zzawq().zzbao(), bundle);
        ((zzcjk)this).zzawm().zzc("auto", "_e", bundle);
        this.zzjjc = l;
        this.zzjje.cancel();
        this.zzjje.zzs(Math.max(0L, 3600000L - this.zzawz().zzjdi.get()));
        return true;
    }
}

