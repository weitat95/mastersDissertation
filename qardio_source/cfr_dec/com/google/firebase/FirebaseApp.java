/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Application
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.util.Log
 */
package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzs;
import com.google.firebase.FirebaseOptions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseApp {
    private static final Object sLock;
    static final Map<String, FirebaseApp> zzifg;
    private static final List<String> zzman;
    private static final List<String> zzmao;
    private static final List<String> zzmap;
    private static final List<String> zzmaq;
    private static final Set<String> zzmar;
    private final Context mApplicationContext;
    private final String mName;
    private final FirebaseOptions zzmas;
    private final AtomicBoolean zzmat = new AtomicBoolean(false);
    private final AtomicBoolean zzmau = new AtomicBoolean();
    private final List<Object> zzmav = new CopyOnWriteArrayList<Object>();
    private final List<zza> zzmaw = new CopyOnWriteArrayList<zza>();
    private final List<Object> zzmax = new CopyOnWriteArrayList<Object>();
    private zzb zzmaz;

    static {
        zzman = Arrays.asList("com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId");
        zzmao = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
        zzmap = Arrays.asList("com.google.android.gms.measurement.AppMeasurement");
        zzmaq = Arrays.asList(new String[0]);
        zzmar = Collections.emptySet();
        sLock = new Object();
        zzifg = new ArrayMap<String, FirebaseApp>();
    }

    private FirebaseApp(Context context, String string2, FirebaseOptions firebaseOptions) {
        this.mApplicationContext = zzbq.checkNotNull(context);
        this.mName = zzbq.zzgm(string2);
        this.zzmas = zzbq.checkNotNull(firebaseOptions);
        this.zzmaz = new com.google.firebase.internal.zza();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FirebaseApp getInstance() {
        Object object = sLock;
        synchronized (object) {
            Object object2 = zzifg.get("[DEFAULT]");
            if (object2 == null) {
                object2 = zzs.zzamo();
                throw new IllegalStateException(new StringBuilder(String.valueOf(object2).length() + 116).append("Default FirebaseApp is not initialized in this process ").append((String)object2).append(". Make sure to call FirebaseApp.initializeApp(Context) first.").toString());
            }
            return object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FirebaseApp initializeApp(Context object) {
        Object object2 = sLock;
        synchronized (object2) {
            if (zzifg.containsKey("[DEFAULT]")) {
                return FirebaseApp.getInstance();
            }
            FirebaseOptions firebaseOptions = FirebaseOptions.fromResource(object);
            if (firebaseOptions != null) return FirebaseApp.initializeApp(object, firebaseOptions);
            return null;
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return FirebaseApp.initializeApp(context, firebaseOptions, "[DEFAULT]");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FirebaseApp initializeApp(Context object, FirebaseOptions firebaseOptions, String string2) {
        com.google.firebase.internal.zzb.zzew((Context)object);
        if (object.getApplicationContext() instanceof Application) {
            zzk.zza((Application)object.getApplicationContext());
            zzk.zzahb().zza(new com.google.firebase.zza());
        }
        string2 = string2.trim();
        if (object.getApplicationContext() != null) {
            object = object.getApplicationContext();
        }
        Object object2 = sLock;
        synchronized (object2) {
            boolean bl = !zzifg.containsKey(string2);
            zzbq.zza(bl, new StringBuilder(String.valueOf(string2).length() + 33).append("FirebaseApp name ").append(string2).append(" already exists!").toString());
            zzbq.checkNotNull(object, "Application context cannot be null.");
            object = new FirebaseApp((Context)object, string2, firebaseOptions);
            zzifg.put(string2, (FirebaseApp)object);
        }
        com.google.firebase.internal.zzb.zzg((FirebaseApp)object);
        super.zza(FirebaseApp.class, object, zzman);
        if (((FirebaseApp)object).zzbqo()) {
            super.zza(FirebaseApp.class, object, zzmao);
            super.zza((Class<T>)Context.class, (T)((FirebaseApp)object).getApplicationContext(), (Iterable<String>)zzmap);
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final <T> void zza(Class<T> var1_1, T var2_3, Iterable<String> var3_4) {
        var5_6 = ContextCompat.isDeviceProtectedStorage(this.mApplicationContext);
        if (var5_6) {
            zzc.zzes(this.mApplicationContext);
        }
        var6_7 = var3_4.iterator();
        while (var6_7.hasNext() != false) {
            var3_4 = (String)var6_7.next();
            if (!var5_6) ** GOTO lbl10
            try {
                if (!FirebaseApp.zzmaq.contains(var3_4)) continue;
lbl10:
                // 2 sources
                if (!Modifier.isPublic(var4_8 = (var7_9 = Class.forName((String)var3_4).getMethod("getInstance", new Class[]{var1_1})).getModifiers()) || !Modifier.isStatic(var4_8)) continue;
                var7_9.invoke(null, new Object[]{var2_3});
            }
            catch (ClassNotFoundException var7_10) {
                if (FirebaseApp.zzmar.contains(var3_4)) {
                    throw new IllegalStateException(String.valueOf(var3_4).concat(" is missing, but is required. Check if it has been removed by Proguard."));
                }
                Log.d((String)"FirebaseApp", (String)String.valueOf(var3_4).concat(" is not linked. Skipping initialization."));
            }
            catch (NoSuchMethodException var1_2) {
                throw new IllegalStateException(String.valueOf(var3_4).concat("#getInstance has been removed by Proguard. Add keep rule to prevent it."));
            }
            catch (InvocationTargetException var3_5) {
                Log.wtf((String)"FirebaseApp", (String)"Firebase API initialization failure.", (Throwable)var3_5);
            }
            catch (IllegalAccessException var7_11) {
                var3_4 = String.valueOf(var3_4);
                var3_4 = var3_4.length() != 0 ? "Failed to initialize ".concat((String)var3_4) : new String("Failed to initialize ");
                Log.wtf((String)"FirebaseApp", (String)var3_4, (Throwable)var7_11);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzbf(boolean bl) {
        Object object = sLock;
        synchronized (object) {
            ArrayList<FirebaseApp> arrayList = new ArrayList<FirebaseApp>(zzifg.values());
            int n = arrayList.size();
            int n2 = 0;
            while (n2 < n) {
                int n3;
                FirebaseApp firebaseApp = arrayList.get(n2);
                n2 = n3 = n2 + 1;
                if (!firebaseApp.zzmat.get()) continue;
                firebaseApp.zzcd(bl);
                n2 = n3;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzbqn() {
        boolean bl = !this.zzmau.get();
        zzbq.zza(bl, "FirebaseApp was deleted");
    }

    private final void zzbqr() {
        this.zza(FirebaseApp.class, this, zzman);
        if (this.zzbqo()) {
            this.zza(FirebaseApp.class, this, zzmao);
            this.zza((Class<T>)Context.class, (T)this.mApplicationContext, (Iterable<String>)zzmap);
        }
    }

    private final void zzcd(boolean bl) {
        Log.d((String)"FirebaseApp", (String)"Notifying background state change listeners.");
        Iterator<zza> iterator = this.zzmaw.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzbf(bl);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof FirebaseApp)) {
            return false;
        }
        return this.mName.equals(((FirebaseApp)object).getName());
    }

    public Context getApplicationContext() {
        this.zzbqn();
        return this.mApplicationContext;
    }

    public String getName() {
        this.zzbqn();
        return this.mName;
    }

    public FirebaseOptions getOptions() {
        this.zzbqn();
        return this.zzmas;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public String toString() {
        return zzbg.zzx(this).zzg("name", this.mName).zzg("options", this.zzmas).toString();
    }

    public final boolean zzbqo() {
        return "[DEFAULT]".equals(this.getName());
    }

    public static interface zza {
        public void zzbf(boolean var1);
    }

    public static interface zzb {
    }

    @TargetApi(value=24)
    static final class zzc
    extends BroadcastReceiver {
        private static AtomicReference<zzc> zzmba = new AtomicReference();
        private final Context mApplicationContext;

        private zzc(Context context) {
            this.mApplicationContext = context;
        }

        private static void zzer(Context context) {
            zzc zzc2;
            if (zzmba.get() == null && zzmba.compareAndSet(null, zzc2 = new zzc(context))) {
                context.registerReceiver((BroadcastReceiver)zzc2, new IntentFilter("android.intent.action.USER_UNLOCKED"));
            }
        }

        static /* synthetic */ void zzes(Context context) {
            zzc.zzer(context);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void onReceive(Context object, Intent object2) {
            object = sLock;
            synchronized (object) {
                object2 = zzifg.values().iterator();
                do {
                    if (!object2.hasNext()) {
                        // MONITOREXIT [2, 3, 4] lbl6 : MonitorExitStatement: MONITOREXIT : var1_1 /* !! */ 
                        this.mApplicationContext.unregisterReceiver((BroadcastReceiver)this);
                        return;
                    }
                    ((FirebaseApp)object2.next()).zzbqr();
                } while (true);
            }
        }
    }

}

