/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.zzaa;
import com.google.firebase.iid.zzu;
import com.google.firebase.iid.zzv;
import com.google.firebase.iid.zzy;
import com.google.firebase.iid.zzz;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirebaseInstanceId {
    private static Map<String, FirebaseInstanceId> zzifg;
    private static final long zznyp;
    private static zzy zznyq;
    private static ScheduledThreadPoolExecutor zznyr;
    private KeyPair zzifj;
    private final FirebaseApp zzmki;
    private final zzu zznys;
    private final zzv zznyt;
    private boolean zznyu = false;

    static {
        zznyp = TimeUnit.HOURS.toSeconds(8L);
        zzifg = new ArrayMap<String, FirebaseInstanceId>();
    }

    private FirebaseInstanceId(FirebaseApp object) {
        this.zzmki = object;
        if (zzu.zzf((FirebaseApp)object) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        this.zznys = new zzu(((FirebaseApp)object).getApplicationContext());
        this.zznyt = new zzv(((FirebaseApp)object).getApplicationContext(), this.zznys);
        object = this.zzciu();
        if (object == null || ((zzz)object).zzro(this.zznys.zzcjg()) || zznyq.zzcjm() != null) {
            this.startSync();
        }
    }

    public static FirebaseInstanceId getInstance() {
        return FirebaseInstanceId.getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static FirebaseInstanceId getInstance(FirebaseApp firebaseApp) {
        synchronized (FirebaseInstanceId.class) {
            FirebaseInstanceId firebaseInstanceId;
            block6: {
                FirebaseInstanceId firebaseInstanceId2;
                firebaseInstanceId = firebaseInstanceId2 = zzifg.get(firebaseApp.getOptions().getApplicationId());
                if (firebaseInstanceId2 != null) break block6;
                if (zznyq == null) {
                    zznyq = new zzy(firebaseApp.getApplicationContext());
                }
                firebaseInstanceId = new FirebaseInstanceId(firebaseApp);
                zzifg.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
            }
            return firebaseInstanceId;
            finally {
            }
        }
    }

    private final void startSync() {
        synchronized (this) {
            if (!this.zznyu) {
                this.zzcc(0L);
            }
            return;
        }
    }

    private final void zzavf() {
        zznyq.zzrl("");
        this.zzifj = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final String zzb(String var1_1, String var2_2, Bundle var3_3) throws IOException {
        var3_3.putString("scope", var2_2);
        var3_3.putString("sender", var1_1);
        var3_3.putString("subtype", var1_1);
        var3_3.putString("appid", this.getId());
        var3_3.putString("gmp_app_id", this.zzmki.getOptions().getApplicationId());
        var3_3.putString("gmsv", Integer.toString(this.zznys.zzcji()));
        var3_3.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
        var3_3.putString("app_ver", this.zznys.zzcjg());
        var3_3.putString("app_ver_name", this.zznys.zzcjh());
        var3_3.putString("cliv", "fiid-11910000");
        var3_3 = this.zznyt.zzad(var3_3);
        if (var3_3 == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        var1_1 = var3_3.getString("registration_id");
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = var2_2 = var3_3.getString("unregistered");
        if (var2_2 == null) {
            var1_1 = var3_3.getString("error");
            if (var1_1 != null) {
                throw new IOException(var1_1);
            }
        } else lbl-1000:
        // 2 sources
        {
            if (!"RST".equals(var1_1)) {
                if (var1_1.startsWith("RST|") == false) return var1_1;
            }
            this.zzciy();
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        var1_1 = String.valueOf((Object)var3_3);
        Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(var1_1).length() + 20).append("Unexpected response ").append(var1_1).toString(), (Throwable)new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void zzb(Runnable runnable, long l) {
        synchronized (FirebaseInstanceId.class) {
            if (zznyr == null) {
                zznyr = new ScheduledThreadPoolExecutor(1);
            }
            zznyr.schedule(runnable, l, TimeUnit.SECONDS);
            return;
        }
    }

    static zzy zzciw() {
        return zznyq;
    }

    static boolean zzcix() {
        return Log.isLoggable((String)"FirebaseInstanceId", (int)3) || Build.VERSION.SDK_INT == 23 && Log.isLoggable((String)"FirebaseInstanceId", (int)3);
    }

    final FirebaseApp getApp() {
        return this.zzmki;
    }

    public String getId() {
        if (this.zzifj == null) {
            this.zzifj = zznyq.zzrm("");
        }
        if (this.zzifj == null) {
            this.zzifj = zznyq.zzrk("");
        }
        return zzu.zzb(this.zzifj);
    }

    public String getToken() {
        zzz zzz2 = this.zzciu();
        if (zzz2 == null || zzz2.zzro(this.zznys.zzcjg())) {
            this.startSync();
        }
        if (zzz2 != null) {
            return zzz2.zzldj;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getToken(String string2, String string3) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Object object = zznyq.zzp("", string2, string3);
        if (object != null && !((zzz)object).zzro(this.zznys.zzcjg())) {
            return ((zzz)object).zzldj;
        }
        String string4 = this.zzb(string2, string3, new Bundle());
        object = string4;
        if (string4 == null) return object;
        zznyq.zza("", string2, string3, string4, this.zznys.zzcjg());
        return string4;
    }

    final void zzcc(long l) {
        synchronized (this) {
            long l2 = Math.min(Math.max(30L, l << 1), zznyp);
            FirebaseInstanceId.zzb(new zzaa(this, this.zznys, l2), l);
            this.zznyu = true;
            return;
        }
    }

    final zzz zzciu() {
        return zznyq.zzp("", zzu.zzf(this.zzmki), "*");
    }

    final String zzciv() throws IOException {
        return this.getToken(zzu.zzf(this.zzmki), "*");
    }

    final void zzciy() {
        zznyq.zzavj();
        this.zzavf();
        this.startSync();
    }

    final void zzciz() {
        zznyq.zzia("");
        this.startSync();
    }

    final void zzcr(boolean bl) {
        synchronized (this) {
            this.zznyu = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzrg(String string2) throws IOException {
        Object object = this.zzciu();
        if (object == null || ((zzz)object).zzro(this.zznys.zzcjg())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String string3 = String.valueOf("/topics/");
        String string4 = String.valueOf(string2);
        string3 = string4.length() != 0 ? string3.concat(string4) : new String(string3);
        bundle.putString("gcm.topic", string3);
        string3 = ((zzz)object).zzldj;
        object = String.valueOf("/topics/");
        string2 = String.valueOf(string2);
        string2 = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
        this.zzb(string3, string2, bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzrh(String string2) throws IOException {
        Object object = this.zzciu();
        if (object == null || ((zzz)object).zzro(this.zznys.zzcjg())) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String string3 = String.valueOf("/topics/");
        String string4 = String.valueOf(string2);
        string3 = string4.length() != 0 ? string3.concat(string4) : new String(string3);
        bundle.putString("gcm.topic", string3);
        bundle.putString("delete", "1");
        string3 = ((zzz)object).zzldj;
        object = String.valueOf("/topics/");
        string2 = String.valueOf(string2);
        string2 = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
        this.zzb(string3, string2, bundle);
    }
}

