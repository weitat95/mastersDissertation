/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzapi;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqb;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqf;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzarq;
import com.google.android.gms.internal.zzasl;
import java.util.HashMap;
import java.util.Map;

final class zzn
implements Runnable {
    private /* synthetic */ Map zzdqm;
    private /* synthetic */ boolean zzdqn;
    private /* synthetic */ String zzdqo;
    private /* synthetic */ long zzdqp;
    private /* synthetic */ boolean zzdqq;
    private /* synthetic */ boolean zzdqr;
    private /* synthetic */ String zzdqs;
    private /* synthetic */ Tracker zzdqt;

    zzn(Tracker tracker, Map map, boolean bl, String string2, long l, boolean bl2, boolean bl3, String string3) {
        this.zzdqt = tracker;
        this.zzdqm = map;
        this.zzdqn = bl;
        this.zzdqo = string2;
        this.zzdqp = l;
        this.zzdqq = bl2;
        this.zzdqr = bl3;
        this.zzdqs = string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        double d;
        long l;
        boolean bl = true;
        if (Tracker.zza(this.zzdqt).zzvg()) {
            this.zzdqm.put("sc", "start");
        }
        Object object = this.zzdqm;
        Object object2 = this.zzdqt.zzww();
        zzbq.zzgn("getClientId can not be called from the main thread");
        zzasl.zzc((Map<String, String>)object, "cid", ((zza)object2).zzum().zzxl().zzyk());
        object = (String)this.zzdqm.get("sf");
        if (object != null && zzasl.zza(d = zzasl.zza((String)object, 100.0), (String)this.zzdqm.get("cid"))) {
            this.zzdqt.zzb("Sampling enabled. Hit sampled out. sample rate", d);
            return;
        }
        object = Tracker.zzb(this.zzdqt);
        if (this.zzdqn) {
            zzasl.zzb((Map<String, String>)this.zzdqm, "ate", ((zzapq)object).zzwb());
            zzasl.zzb((Map<String, String>)this.zzdqm, "adid", ((zzapq)object).zzwi());
        } else {
            this.zzdqm.remove("ate");
            this.zzdqm.remove("adid");
        }
        object = Tracker.zzc(this.zzdqt).zzxy();
        zzasl.zzb((Map<String, String>)this.zzdqm, "an", ((zzapd)object).zzvi());
        zzasl.zzb((Map<String, String>)this.zzdqm, "av", ((zzapd)object).zzvj());
        zzasl.zzb((Map<String, String>)this.zzdqm, "aid", ((zzapd)object).getAppId());
        zzasl.zzb((Map<String, String>)this.zzdqm, "aiid", ((zzapd)object).zzvk());
        this.zzdqm.put("v", "1");
        this.zzdqm.put("_v", zzaqb.zzdtc);
        zzasl.zzb((Map<String, String>)this.zzdqm, "ul", Tracker.zzd(this.zzdqt).zzzc().getLanguage());
        zzasl.zzb((Map<String, String>)this.zzdqm, "sr", Tracker.zze(this.zzdqt).zzzd());
        boolean bl2 = this.zzdqo.equals("transaction") || this.zzdqo.equals("item");
        if (!bl2 && !Tracker.zzf(this.zzdqt).zzzn()) {
            Tracker.zzg(this.zzdqt).zzf(this.zzdqm, "Too many hits sent too quickly, rate limiting invoked");
            return;
        }
        long l2 = l = zzasl.zzei((String)this.zzdqm.get("ht"));
        if (l == 0L) {
            l2 = this.zzdqp;
        }
        if (this.zzdqq) {
            object = new zzarq(this.zzdqt, this.zzdqm, l2, this.zzdqr);
            Tracker.zzh(this.zzdqt).zzc("Dry run enabled. Would have sent hit", object);
            return;
        }
        object = (String)this.zzdqm.get("cid");
        object2 = new HashMap();
        zzasl.zza((Map<String, String>)object2, "uid", this.zzdqm);
        zzasl.zza((Map<String, String>)object2, "an", this.zzdqm);
        zzasl.zza((Map<String, String>)object2, "aid", this.zzdqm);
        zzasl.zza((Map<String, String>)object2, "av", this.zzdqm);
        zzasl.zza((Map<String, String>)object2, "aiid", this.zzdqm);
        String string2 = this.zzdqs;
        if (TextUtils.isEmpty((CharSequence)((CharSequence)this.zzdqm.get("adid")))) {
            bl = false;
        }
        object = new zzaqf(0L, (String)object, string2, bl, 0L, (Map<String, String>)object2);
        l = Tracker.zzi(this.zzdqt).zza((zzaqf)object);
        this.zzdqm.put("_s", String.valueOf(l));
        object = new zzarq(this.zzdqt, this.zzdqm, l2, this.zzdqr);
        Tracker.zzj(this.zzdqt).zza((zzarq)object);
    }
}

