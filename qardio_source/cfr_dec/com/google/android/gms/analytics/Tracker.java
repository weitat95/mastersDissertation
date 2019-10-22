/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.analytics.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzarh;
import com.google.android.gms.internal.zzart;
import com.google.android.gms.internal.zzarv;
import com.google.android.gms.internal.zzask;
import com.google.android.gms.internal.zzasl;
import com.google.android.gms.internal.zzasm;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Tracker
extends zzaqa {
    private final Map<String, String> zzbsr = new HashMap<String, String>();
    private boolean zzdqg;
    private final Map<String, String> zzdqh = new HashMap<String, String>();
    private final zzart zzdqi;
    private final zza zzdqj;
    private ExceptionReporter zzdqk;
    private zzask zzdql;

    Tracker(zzaqc zzaqc2, String string2, zzart zzart2) {
        super(zzaqc2);
        if (string2 != null) {
            this.zzbsr.put("&tid", string2);
        }
        this.zzbsr.put("useSecure", "1");
        this.zzbsr.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzdqi = new zzart("tracking", this.zzws());
        this.zzdqj = new zza(this, zzaqc2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zza(Map.Entry<String, String> entry) {
        String string2 = entry.getKey();
        if (!string2.startsWith("&")) return null;
        if (string2.length() < 2) {
            return null;
        }
        boolean bl = true;
        if (bl) return entry.getKey().substring(1);
        return null;
    }

    static /* synthetic */ zzapq zzb(Tracker tracker) {
        return tracker.zzxc();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zzb(Map<String, String> iterator, Map<String, String> map) {
        zzbq.checkNotNull(map);
        if (iterator != null) {
            for (Map.Entry<String, String> entry : iterator.entrySet()) {
                String string2 = Tracker.zza(entry);
                if (string2 == null) continue;
                map.put(string2, entry.getValue());
            }
        }
    }

    static /* synthetic */ zzaqn zzc(Tracker tracker) {
        return tracker.zzxd();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zzc(Map<String, String> iterator, Map<String, String> map) {
        zzbq.checkNotNull(map);
        if (iterator != null) {
            for (Map.Entry<String, String> entry : iterator.entrySet()) {
                String string2 = Tracker.zza(entry);
                if (string2 == null || map.containsKey(string2)) continue;
                map.put(string2, entry.getValue());
            }
        }
    }

    static /* synthetic */ zzarh zzd(Tracker tracker) {
        return tracker.zzxe();
    }

    static /* synthetic */ zzarh zze(Tracker tracker) {
        return tracker.zzxe();
    }

    static /* synthetic */ zzart zzf(Tracker tracker) {
        return tracker.zzdqi;
    }

    static /* synthetic */ zzarv zzg(Tracker tracker) {
        return tracker.zzwt();
    }

    static /* synthetic */ zzarv zzh(Tracker tracker) {
        return tracker.zzwt();
    }

    static /* synthetic */ zzapr zzi(Tracker tracker) {
        return tracker.zzwx();
    }

    static /* synthetic */ zzapr zzj(Tracker tracker) {
        return tracker.zzwx();
    }

    public void enableAdvertisingIdCollection(boolean bl) {
        this.zzdqg = bl;
    }

    public void enableAutoActivityTracking(boolean bl) {
        this.zzdqj.enableAutoActivityTracking(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enableExceptionReporting(boolean bl) {
        synchronized (this) {
            boolean bl2 = this.zzdqk != null;
            if (bl2 == bl) {
                return;
            }
            if (bl) {
                Context context = this.getContext();
                this.zzdqk = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), context);
                Thread.setDefaultUncaughtExceptionHandler(this.zzdqk);
                this.zzdu("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzdqk.zzuq());
                this.zzdu("Uncaught exceptions will not be reported to Google Analytics");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void send(Map<String, String> object) {
        long l = this.zzws().currentTimeMillis();
        if (this.zzww().getAppOptOut()) {
            this.zzdv("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean bl = this.zzww().isDryRunEnabled();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Tracker.zzb(this.zzbsr, hashMap);
        Tracker.zzb(object, hashMap);
        boolean bl2 = zzasl.zzd(this.zzbsr.get("useSecure"), true);
        Tracker.zzc(this.zzdqh, hashMap);
        this.zzdqh.clear();
        object = (String)hashMap.get("t");
        if (TextUtils.isEmpty((CharSequence)object)) {
            this.zzwt().zzf(hashMap, "Missing hit type parameter");
            return;
        }
        String string2 = (String)hashMap.get("tid");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            this.zzwt().zzf(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean bl3 = this.zzdqg;
        synchronized (this) {
            if ("screenview".equalsIgnoreCase((String)object) || "pageview".equalsIgnoreCase((String)object) || "appview".equalsIgnoreCase((String)object) || TextUtils.isEmpty((CharSequence)object)) {
                int n;
                int n2 = n = Integer.parseInt(this.zzbsr.get("&a")) + 1;
                if (n >= Integer.MAX_VALUE) {
                    n2 = 1;
                }
                this.zzbsr.put("&a", Integer.toString(n2));
            }
        }
        this.zzwv().zzc(new zzn(this, hashMap, bl3, (String)object, l, bl, bl2, string2));
    }

    public void set(String string2, String string3) {
        zzbq.checkNotNull(string2, "Key should be non-null");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return;
        }
        this.zzbsr.put(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCampaignParamsOnNextHit(Uri object) {
        block13: {
            block12: {
                if (object == null || object.isOpaque() || TextUtils.isEmpty((CharSequence)(object = object.getQueryParameter("referrer")))) break block12;
                String string2 = (object = Uri.parse((String)(object = ((String)(object = String.valueOf(object))).length() != 0 ? "http://hostname/?".concat((String)object) : new String("http://hostname/?")))).getQueryParameter("utm_id");
                if (string2 != null) {
                    this.zzdqh.put("&ci", string2);
                }
                if ((string2 = object.getQueryParameter("anid")) != null) {
                    this.zzdqh.put("&anid", string2);
                }
                if ((string2 = object.getQueryParameter("utm_campaign")) != null) {
                    this.zzdqh.put("&cn", string2);
                }
                if ((string2 = object.getQueryParameter("utm_content")) != null) {
                    this.zzdqh.put("&cc", string2);
                }
                if ((string2 = object.getQueryParameter("utm_medium")) != null) {
                    this.zzdqh.put("&cm", string2);
                }
                if ((string2 = object.getQueryParameter("utm_source")) != null) {
                    this.zzdqh.put("&cs", string2);
                }
                if ((string2 = object.getQueryParameter("utm_term")) != null) {
                    this.zzdqh.put("&ck", string2);
                }
                if ((string2 = object.getQueryParameter("dclid")) != null) {
                    this.zzdqh.put("&dclid", string2);
                }
                if ((string2 = object.getQueryParameter("gclid")) != null) {
                    this.zzdqh.put("&gclid", string2);
                }
                if ((object = object.getQueryParameter("aclid")) != null) break block13;
            }
            return;
        }
        this.zzdqh.put("&aclid", (String)object);
    }

    public void setScreenName(String string2) {
        this.set("&cd", string2);
    }

    public void setSessionTimeout(long l) {
        this.zzdqj.setSessionTimeout(1000L * l);
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zza(zzask object) {
        boolean bl;
        boolean bl2 = true;
        this.zzdu("Loading Tracker config values");
        this.zzdql = object;
        int n = this.zzdql.zzdom != null ? 1 : 0;
        if (n != 0) {
            object = this.zzdql.zzdom;
            this.set("&tid", (String)object);
            this.zza("trackingId loaded", object);
        }
        if ((n = this.zzdql.zzdzd >= 0.0 ? 1 : 0) != 0) {
            object = Double.toString(this.zzdql.zzdzd);
            this.set("&sf", (String)object);
            this.zza("Sample frequency loaded", object);
        }
        if ((n = this.zzdql.zzdze >= 0 ? 1 : 0) != 0) {
            n = this.zzdql.zzdze;
            this.setSessionTimeout(n);
            this.zza("Session timeout loaded", n);
        }
        if (this.zzdql.zzdzf != -1) {
            bl = this.zzdql.zzdzf == 1;
            this.enableAutoActivityTracking(bl);
            this.zza("Auto activity tracking loaded", bl);
        }
        if (this.zzdql.zzdzg != -1) {
            bl = this.zzdql.zzdzg == 1;
            if (bl) {
                this.set("&aip", "1");
            }
            this.zza("Anonymize ip loaded", bl);
        }
        bl = this.zzdql.zzdzh == 1 ? bl2 : false;
        this.enableExceptionReporting(bl);
    }

    @Override
    protected final void zzvf() {
        this.zzdqj.initialize();
        String string2 = this.zzwz().zzvi();
        if (string2 != null) {
            this.set("&an", string2);
        }
        if ((string2 = this.zzwz().zzvj()) != null) {
            this.set("&av", string2);
        }
    }

    final class zza
    extends zzaqa
    implements GoogleAnalytics.zza {
        private /* synthetic */ Tracker zzdqt;
        private boolean zzdqu;
        private int zzdqv;
        private long zzdqw;
        private boolean zzdqx;
        private long zzdqy;

        protected zza(Tracker tracker, zzaqc zzaqc2) {
            this.zzdqt = tracker;
            super(zzaqc2);
            this.zzdqw = -1L;
        }

        private final void zzvh() {
            if (this.zzdqw >= 0L || this.zzdqu) {
                this.zzww().zza(this.zzdqt.zzdqj);
                return;
            }
            this.zzww().zzb(this.zzdqt.zzdqj);
        }

        public final void enableAutoActivityTracking(boolean bl) {
            this.zzdqu = bl;
            this.zzvh();
        }

        public final void setSessionTimeout(long l) {
            this.zzdqw = l;
            this.zzvh();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public final void zzl(Activity object) {
            boolean bl;
            if (this.zzdqv == 0 && (bl = this.zzws().elapsedRealtime() >= this.zzdqy + Math.max(1000L, this.zzdqw))) {
                this.zzdqx = true;
            }
            ++this.zzdqv;
            if (this.zzdqu) {
                void var3_6;
                Intent intent = object.getIntent();
                if (intent != null) {
                    this.zzdqt.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("&t", "screenview");
                Tracker tracker = this.zzdqt;
                if (this.zzdqt.zzdql != null) {
                    Object object2 = this.zzdqt.zzdql;
                    String string2 = object.getClass().getCanonicalName();
                    object2 = ((zzask)object2).zzdzi.get(string2);
                    if (object2 != null) {
                        Object object3 = object2;
                    }
                } else {
                    String string3 = object.getClass().getCanonicalName();
                }
                tracker.set("&cd", (String)var3_6);
                if (TextUtils.isEmpty((CharSequence)((CharSequence)hashMap.get("&dr")))) {
                    zzbq.checkNotNull(object);
                    object = object.getIntent();
                    if (object == null) {
                        object = null;
                    } else {
                        String string4 = object.getStringExtra("android.intent.extra.REFERRER_NAME");
                        object = string4;
                        if (TextUtils.isEmpty((CharSequence)string4)) {
                            object = null;
                        }
                    }
                    if (!TextUtils.isEmpty((CharSequence)object)) {
                        hashMap.put("&dr", (String)object);
                    }
                }
                this.zzdqt.send(hashMap);
            }
        }

        @Override
        public final void zzm(Activity activity) {
            --this.zzdqv;
            this.zzdqv = Math.max(0, this.zzdqv);
            if (this.zzdqv == 0) {
                this.zzdqy = this.zzws().elapsedRealtime();
            }
        }

        @Override
        protected final void zzvf() {
        }

        public final boolean zzvg() {
            synchronized (this) {
                boolean bl = this.zzdqx;
                this.zzdqx = false;
                return bl;
            }
        }
    }

}

