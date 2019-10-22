/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcge;
import com.google.android.gms.internal.zzcgf;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.Map;
import java.util.Set;

public final class zzcgd
extends zzcjk {
    private final Map<String, Long> zziwr;
    private final Map<String, Integer> zziws = new ArrayMap<String, Integer>();
    private long zziwt;

    public zzcgd(zzcim zzcim2) {
        super(zzcim2);
        this.zziwr = new ArrayMap<String, Long>();
    }

    private final void zza(long l, AppMeasurement.zzb zzb2) {
        if (zzb2 == null) {
            ((zzcjk)this).zzawy().zzazj().log("Not logging ad exposure. No active activity");
            return;
        }
        if (l < 1000L) {
            ((zzcjk)this).zzawy().zzazj().zzj("Not logging ad exposure. Less than 1000 ms. exposure", l);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("_xt", l);
        zzckc.zza(zzb2, bundle);
        ((zzcjk)this).zzawm().zzc("am", "_xa", bundle);
    }

    static /* synthetic */ void zza(zzcgd zzcgd2, long l) {
        zzcgd2.zzak(l);
    }

    static /* synthetic */ void zza(zzcgd zzcgd2, String string2, long l) {
        zzcgd2.zzd(string2, l);
    }

    private final void zza(String string2, long l, AppMeasurement.zzb zzb2) {
        if (zzb2 == null) {
            ((zzcjk)this).zzawy().zzazj().log("Not logging ad unit exposure. No active activity");
            return;
        }
        if (l < 1000L) {
            ((zzcjk)this).zzawy().zzazj().zzj("Not logging ad unit exposure. Less than 1000 ms. exposure", l);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("_ai", string2);
        bundle.putLong("_xt", l);
        zzckc.zza(zzb2, bundle);
        ((zzcjk)this).zzawm().zzc("am", "_xu", bundle);
    }

    private final void zzak(long l) {
        for (String string2 : this.zziwr.keySet()) {
            this.zziwr.put(string2, l);
        }
        if (!this.zziwr.isEmpty()) {
            this.zziwt = l;
        }
    }

    static /* synthetic */ void zzb(zzcgd zzcgd2, String string2, long l) {
        zzcgd2.zze(string2, l);
    }

    private final void zzd(String string2, long l) {
        Integer n;
        ((zzcjk)this).zzve();
        zzbq.zzgm(string2);
        if (this.zziws.isEmpty()) {
            this.zziwt = l;
        }
        if ((n = this.zziws.get(string2)) != null) {
            this.zziws.put(string2, n + 1);
            return;
        }
        if (this.zziws.size() >= 100) {
            ((zzcjk)this).zzawy().zzazf().log("Too many ads visible");
            return;
        }
        this.zziws.put(string2, 1);
        this.zziwr.put(string2, l);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zze(String string2, long l) {
        ((zzcjk)this).zzve();
        zzbq.zzgm(string2);
        Number number = this.zziws.get(string2);
        if (number == null) {
            ((zzcjk)this).zzawy().zzazd().zzj("Call to endAdUnitExposure for unknown ad unit id", string2);
            return;
        }
        zzckf zzckf2 = ((zzcjk)this).zzawq().zzbao();
        int n = (Integer)number - 1;
        if (n != 0) {
            this.zziws.put(string2, n);
            return;
        }
        this.zziws.remove(string2);
        number = this.zziwr.get(string2);
        if (number == null) {
            ((zzcjk)this).zzawy().zzazd().log("First ad unit exposure time was never set");
        } else {
            long l2 = (Long)number;
            this.zziwr.remove(string2);
            this.zza(string2, l - l2, zzckf2);
        }
        if (this.zziws.isEmpty()) {
            if (this.zziwt != 0L) {
                this.zza(l - this.zziwt, zzckf2);
                this.zziwt = 0L;
                return;
            }
            ((zzcjk)this).zzawy().zzazd().log("First ad exposure time was never set");
        }
    }

    public final void beginAdUnitExposure(String string2) {
        if (string2 == null || string2.length() == 0) {
            ((zzcjk)this).zzawy().zzazd().log("Ad unit id must be a non-empty string");
            return;
        }
        long l = ((zzcjk)this).zzws().elapsedRealtime();
        ((zzcjk)this).zzawx().zzg(new zzcge(this, string2, l));
    }

    public final void endAdUnitExposure(String string2) {
        if (string2 == null || string2.length() == 0) {
            ((zzcjk)this).zzawy().zzazd().log("Ad unit id must be a non-empty string");
            return;
        }
        long l = ((zzcjk)this).zzws().elapsedRealtime();
        ((zzcjk)this).zzawx().zzg(new zzcgf(this, string2, l));
    }

    public final void zzaj(long l) {
        zzckf zzckf2 = ((zzcjk)this).zzawq().zzbao();
        for (String string2 : this.zziwr.keySet()) {
            this.zza(string2, l - this.zziwr.get(string2), zzckf2);
        }
        if (!this.zziwr.isEmpty()) {
            this.zza(l - this.zziwt, zzckf2);
        }
        this.zzak(l);
    }
}

