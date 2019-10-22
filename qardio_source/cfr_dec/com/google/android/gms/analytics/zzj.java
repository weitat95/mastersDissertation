/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Process
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.analytics.zzl;
import com.google.android.gms.analytics.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzapi;
import com.google.android.gms.internal.zzasl;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzj {
    private static volatile zzj zzdpv;
    private final Context mContext;
    private final List<Object> zzdpw;
    private final zze zzdpx;
    private final zza zzdpy;
    private volatile zzapd zzdpz;
    private Thread.UncaughtExceptionHandler zzdqa;

    private zzj(Context context) {
        context = context.getApplicationContext();
        zzbq.checkNotNull(context);
        this.mContext = context;
        this.zzdpy = new zza();
        this.zzdpw = new CopyOnWriteArrayList<Object>();
        this.zzdpx = new zze();
    }

    static /* synthetic */ List zza(zzj zzj2) {
        return zzj2.zzdpw;
    }

    static /* synthetic */ void zza(zzj zzj2, zzg zzg2) {
        zzj.zzb(zzg2);
    }

    static /* synthetic */ Thread.UncaughtExceptionHandler zzb(zzj zzj2) {
        return zzj2.zzdqa;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zzb(zzg zzg2) {
        zzbq.zzgn("deliver should be called from worker thread");
        zzbq.checkArgument(zzg2.zzuw(), "Measurement must be submitted");
        Object object = zzg2.getTransports();
        if (!object.isEmpty()) {
            HashSet<Uri> hashSet = new HashSet<Uri>();
            object = object.iterator();
            while (object.hasNext()) {
                zzm zzm2 = (zzm)object.next();
                Uri uri = zzm2.zzup();
                if (hashSet.contains((Object)uri)) continue;
                hashSet.add(uri);
                zzm2.zzb(zzg2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzj zzbl(Context context) {
        zzbq.checkNotNull(context);
        if (zzdpv == null) {
            synchronized (zzj.class) {
                if (zzdpv == null) {
                    zzdpv = new zzj(context);
                }
            }
        }
        return zzdpv;
    }

    public static void zzve() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final <V> Future<V> zza(Callable<V> object) {
        zzbq.checkNotNull(object);
        if (Thread.currentThread() instanceof zzc) {
            object = new FutureTask<V>((Callable<V>)object);
            ((FutureTask)object).run();
            return object;
        }
        return this.zzdpy.submit(object);
    }

    public final void zza(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzdqa = uncaughtExceptionHandler;
    }

    public final void zzc(Runnable runnable) {
        zzbq.checkNotNull(runnable);
        this.zzdpy.submit(runnable);
    }

    final void zze(zzg zzg2) {
        if (zzg2.zzuz()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        }
        if (zzg2.zzuw()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        }
        zzg2 = zzg2.zzus();
        zzg2.zzux();
        this.zzdpy.execute(new zzk(this, zzg2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final zzapd zzvc() {
        if (this.zzdpz == null) {
            synchronized (this) {
                if (this.zzdpz == null) {
                    String string2;
                    CharSequence charSequence;
                    zzapd zzapd2 = new zzapd();
                    PackageManager packageManager = this.mContext.getPackageManager();
                    String string3 = this.mContext.getPackageName();
                    zzapd2.setAppId(string3);
                    zzapd2.setAppInstallerId(packageManager.getInstallerPackageName(string3));
                    CharSequence charSequence2 = null;
                    String string4 = string3;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
                        charSequence = charSequence2;
                        string2 = string3;
                        if (packageInfo != null) {
                            string4 = string3;
                            charSequence = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            string2 = string3;
                            string4 = string3;
                            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                                string4 = string3;
                                string2 = charSequence.toString();
                            }
                            string4 = string2;
                            charSequence = packageInfo.versionName;
                        }
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        string2 = String.valueOf(string4);
                        string2 = string2.length() != 0 ? "Error retrieving package info: appName set to ".concat(string2) : new String("Error retrieving package info: appName set to ");
                        Log.e((String)"GAv4", (String)string2);
                        charSequence = charSequence2;
                        string2 = string4;
                    }
                    zzapd2.setAppName(string2);
                    zzapd2.setAppVersion((String)charSequence);
                    this.zzdpz = zzapd2;
                }
            }
        }
        return this.zzdpz;
    }

    public final zzapi zzvd() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        zzapi zzapi2 = new zzapi();
        zzapi2.setLanguage(zzasl.zza(Locale.getDefault()));
        zzapi2.zzchl = displayMetrics.widthPixels;
        zzapi2.zzchm = displayMetrics.heightPixels;
        return zzapi2;
    }

    final class zza
    extends ThreadPoolExecutor {
        public zza() {
            super(1, 1, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
            this.setThreadFactory(new zzb(null));
            this.allowCoreThreadTimeOut(true);
        }

        @Override
        protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new zzl(this, runnable, t);
        }
    }

    static final class zzb
    implements ThreadFactory {
        private static final AtomicInteger zzdqe = new AtomicInteger();

        private zzb() {
        }

        /* synthetic */ zzb(zzk zzk2) {
            this();
        }

        @Override
        public final Thread newThread(Runnable runnable) {
            int n = zzdqe.incrementAndGet();
            return new zzc(runnable, new StringBuilder(23).append("measurement-").append(n).toString());
        }
    }

    static final class zzc
    extends Thread {
        zzc(Runnable runnable, String string2) {
            super(runnable, string2);
        }

        @Override
        public final void run() {
            Process.setThreadPriority((int)10);
            super.run();
        }
    }

}

