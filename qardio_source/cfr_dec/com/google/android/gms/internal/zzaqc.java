/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqb;
import com.google.android.gms.internal.zzaqd;
import com.google.android.gms.internal.zzaqe;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzard;
import com.google.android.gms.internal.zzarh;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzarz;
import com.google.android.gms.internal.zzasm;

public class zzaqc {
    private static volatile zzaqc zzdtd;
    private final Context mContext;
    private final zzd zzata;
    private final Context zzdte;
    private final zzard zzdtf;
    private final zzarv zzdtg;
    private final zzj zzdth;
    private final zzapr zzdti;
    private final zzari zzdtj;
    private final zzasm zzdtk;
    private final zzarz zzdtl;
    private final GoogleAnalytics zzdtm;
    private final zzaqu zzdtn;
    private final zzapq zzdto;
    private final zzaqn zzdtp;
    private final zzarh zzdtq;

    private zzaqc(zzaqe object) {
        Object object2 = ((zzaqe)object).getApplicationContext();
        zzbq.checkNotNull(object2, "Application context can't be null");
        Object object3 = ((zzaqe)object).zzxg();
        zzbq.checkNotNull(object3);
        this.mContext = object2;
        this.zzdte = object3;
        this.zzata = zzh.zzamg();
        this.zzdtf = new zzard(this);
        object3 = new zzarv(this);
        ((zzaqa)object3).initialize();
        this.zzdtg = object3;
        object3 = this.zzwt();
        Object object4 = zzaqb.VERSION;
        ((zzapz)object3).zzdw(new StringBuilder(String.valueOf(object4).length() + 134).append("Google Analytics ").append((String)object4).append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4").toString());
        object3 = new zzarz(this);
        ((zzaqa)object3).initialize();
        this.zzdtl = object3;
        object3 = new zzasm(this);
        ((zzaqa)object3).initialize();
        this.zzdtk = object3;
        object = new zzapr(this, (zzaqe)object);
        object3 = new zzaqu(this);
        object4 = new zzapq(this);
        zzaqn zzaqn2 = new zzaqn(this);
        zzarh zzarh2 = new zzarh(this);
        object2 = zzj.zzbl((Context)object2);
        ((zzj)object2).zza(new zzaqd(this));
        this.zzdth = object2;
        object2 = new GoogleAnalytics(this);
        ((zzaqa)object3).initialize();
        this.zzdtn = object3;
        ((zzaqa)object4).initialize();
        this.zzdto = object4;
        zzaqn2.initialize();
        this.zzdtp = zzaqn2;
        zzarh2.initialize();
        this.zzdtq = zzarh2;
        object3 = new zzari(this);
        ((zzaqa)object3).initialize();
        this.zzdtj = object3;
        ((zzaqa)object).initialize();
        this.zzdti = object;
        ((GoogleAnalytics)object2).initialize();
        this.zzdtm = object2;
        ((zzapr)object).start();
    }

    private static void zza(zzaqa zzaqa2) {
        zzbq.checkNotNull(zzaqa2, "Analytics service not created/initialized");
        zzbq.checkArgument(zzaqa2.isInitialized(), "Analytics service not initialized");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzaqc zzbm(Context object) {
        zzbq.checkNotNull(object);
        if (zzdtd == null) {
            synchronized (zzaqc.class) {
                if (zzdtd == null) {
                    zzd zzd2 = zzh.zzamg();
                    long l = zzd2.elapsedRealtime();
                    object = new zzaqc(new zzaqe((Context)object));
                    zzdtd = object;
                    GoogleAnalytics.zzur();
                    l = zzd2.elapsedRealtime() - l;
                    long l2 = zzarl.zzdxm.get();
                    if (l > l2) {
                        ((zzaqc)object).zzwt().zzc("Slow initialization (ms)", l, l2);
                    }
                }
            }
        }
        return zzdtd;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final zzd zzws() {
        return this.zzata;
    }

    public final zzarv zzwt() {
        zzaqc.zza(this.zzdtg);
        return this.zzdtg;
    }

    public final zzard zzwu() {
        return this.zzdtf;
    }

    public final zzj zzwv() {
        zzbq.checkNotNull(this.zzdth);
        return this.zzdth;
    }

    public final zzapr zzwx() {
        zzaqc.zza(this.zzdti);
        return this.zzdti;
    }

    public final zzari zzwy() {
        zzaqc.zza(this.zzdtj);
        return this.zzdtj;
    }

    public final zzasm zzwz() {
        zzaqc.zza(this.zzdtk);
        return this.zzdtk;
    }

    public final zzarz zzxa() {
        zzaqc.zza(this.zzdtl);
        return this.zzdtl;
    }

    public final zzaqn zzxd() {
        zzaqc.zza(this.zzdtp);
        return this.zzdtp;
    }

    public final zzarh zzxe() {
        return this.zzdtq;
    }

    public final Context zzxg() {
        return this.zzdte;
    }

    public final zzarv zzxh() {
        return this.zzdtg;
    }

    public final GoogleAnalytics zzxi() {
        zzbq.checkNotNull(this.zzdtm);
        zzbq.checkArgument(this.zzdtm.isInitialized(), "Analytics instance not initialized");
        return this.zzdtm;
    }

    public final zzarz zzxj() {
        if (this.zzdtl == null || !this.zzdtl.isInitialized()) {
            return null;
        }
        return this.zzdtl;
    }

    public final zzapq zzxk() {
        zzaqc.zza(this.zzdto);
        return this.zzdto;
    }

    public final zzaqu zzxl() {
        zzaqc.zza(this.zzdtn);
        return this.zzdtn;
    }
}

