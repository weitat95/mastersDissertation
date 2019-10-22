/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchy;
import com.google.android.gms.internal.zzchz;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcib;
import com.google.android.gms.internal.zzcic;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzclq;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

final class zzchx
extends zzcjl {
    static final Pair<String, Long> zzjcp = new Pair((Object)"", (Object)0L);
    private SharedPreferences zzdyn;
    public final zzcib zzjcq = new zzcib(this, "health_monitor", Math.max(0L, zzchc.zzjaf.get()), null);
    public final zzcia zzjcr = new zzcia(this, "last_upload", 0L);
    public final zzcia zzjcs = new zzcia(this, "last_upload_attempt", 0L);
    public final zzcia zzjct = new zzcia(this, "backoff", 0L);
    public final zzcia zzjcu = new zzcia(this, "last_delete_stale", 0L);
    public final zzcia zzjcv;
    public final zzcia zzjcw;
    public final zzcic zzjcx;
    private String zzjcy;
    private boolean zzjcz;
    private long zzjda;
    private String zzjdb;
    private long zzjdc;
    private final Object zzjdd;
    public final zzcia zzjde = new zzcia(this, "time_before_start", 10000L);
    public final zzcia zzjdf = new zzcia(this, "session_timeout", 1800000L);
    public final zzchz zzjdg = new zzchz(this, "start_new_session", true);
    public final zzcia zzjdh = new zzcia(this, "last_pause_time", 0L);
    public final zzcia zzjdi = new zzcia(this, "time_active", 0L);
    public boolean zzjdj;

    zzchx(zzcim zzcim2) {
        super(zzcim2);
        this.zzjcv = new zzcia(this, "midnight_offset", 0L);
        this.zzjcw = new zzcia(this, "first_open_time", 0L);
        this.zzjcx = new zzcic(this, "app_instance_id", null);
        this.zzjdd = new Object();
    }

    static /* synthetic */ SharedPreferences zza(zzchx zzchx2) {
        return zzchx2.zzazl();
    }

    private final SharedPreferences zzazl() {
        this.zzve();
        this.zzxf();
        return this.zzdyn;
    }

    final void setMeasurementEnabled(boolean bl) {
        this.zzve();
        this.zzawy().zzazj().zzj("Setting measurementEnabled", bl);
        SharedPreferences.Editor editor = this.zzazl().edit();
        editor.putBoolean("measurement_enabled", bl);
        editor.apply();
    }

    @Override
    protected final boolean zzaxz() {
        return true;
    }

    @Override
    protected final void zzayy() {
        this.zzdyn = this.getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzjdj = this.zzdyn.getBoolean("has_been_opened", false);
        if (!this.zzjdj) {
            SharedPreferences.Editor editor = this.zzdyn.edit();
            editor.putBoolean("has_been_opened", true);
            editor.apply();
        }
    }

    final String zzazm() {
        this.zzve();
        return this.zzazl().getString("gmp_app_id", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final String zzazn() {
        Object object = this.zzjdd;
        synchronized (object) {
            if (Math.abs(this.zzws().elapsedRealtime() - this.zzjdc) >= 1000L) return null;
            return this.zzjdb;
        }
    }

    final Boolean zzazo() {
        this.zzve();
        if (!this.zzazl().contains("use_service")) {
            return null;
        }
        return this.zzazl().getBoolean("use_service", false);
    }

    final void zzazp() {
        boolean bl = true;
        this.zzve();
        this.zzawy().zzazj().log("Clearing collection preferences.");
        boolean bl2 = this.zzazl().contains("measurement_enabled");
        if (bl2) {
            bl = this.zzbn(true);
        }
        SharedPreferences.Editor editor = this.zzazl().edit();
        editor.clear();
        editor.apply();
        if (bl2) {
            this.setMeasurementEnabled(bl);
        }
    }

    protected final String zzazq() {
        this.zzve();
        String string2 = this.zzazl().getString("previous_os_version", null);
        this.zzawo().zzxf();
        String string3 = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty((CharSequence)string3) && !string3.equals(string2)) {
            SharedPreferences.Editor editor = this.zzazl().edit();
            editor.putString("previous_os_version", string3);
            editor.apply();
        }
        return string2;
    }

    final void zzbm(boolean bl) {
        this.zzve();
        this.zzawy().zzazj().zzj("Setting useService", bl);
        SharedPreferences.Editor editor = this.zzazl().edit();
        editor.putBoolean("use_service", bl);
        editor.apply();
    }

    final boolean zzbn(boolean bl) {
        this.zzve();
        return this.zzazl().getBoolean("measurement_enabled", bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final Pair<String, Boolean> zzjm(String object) {
        this.zzve();
        long l = this.zzws().elapsedRealtime();
        if (this.zzjcy != null && l < this.zzjda) {
            return new Pair((Object)this.zzjcy, (Object)this.zzjcz);
        }
        this.zzjda = l + this.zzaxa().zza((String)object, zzchc.zzjae);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            object = AdvertisingIdClient.getAdvertisingIdInfo(this.getContext());
            if (object != null) {
                this.zzjcy = ((AdvertisingIdClient.Info)object).getId();
                this.zzjcz = ((AdvertisingIdClient.Info)object).isLimitAdTrackingEnabled();
            }
            if (this.zzjcy == null) {
                this.zzjcy = "";
            }
        }
        catch (Throwable throwable) {
            this.zzawy().zzazi().zzj("Unable to get advertising id", throwable);
            this.zzjcy = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair((Object)this.zzjcy, (Object)this.zzjcz);
    }

    final String zzjn(String string2) {
        this.zzve();
        string2 = (String)this.zzjm((String)string2).first;
        MessageDigest messageDigest = zzclq.zzek("MD5");
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(string2.getBytes())));
    }

    final void zzjo(String string2) {
        this.zzve();
        SharedPreferences.Editor editor = this.zzazl().edit();
        editor.putString("gmp_app_id", string2);
        editor.apply();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzjp(String string2) {
        Object object = this.zzjdd;
        synchronized (object) {
            this.zzjdb = string2;
            this.zzjdc = this.zzws().elapsedRealtime();
            return;
        }
    }
}

