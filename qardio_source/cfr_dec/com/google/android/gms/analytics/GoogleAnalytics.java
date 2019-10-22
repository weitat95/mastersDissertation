/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzart;
import com.google.android.gms.internal.zzasi;
import com.google.android.gms.internal.zzask;
import com.google.android.gms.internal.zzasm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics
extends com.google.android.gms.analytics.zza {
    private static List<Runnable> zzdou = new ArrayList<Runnable>();
    private boolean zzare;
    private Set<zza> zzdov = new HashSet<zza>();
    private boolean zzdow;
    private boolean zzdox;
    private volatile boolean zzdoy;

    public GoogleAnalytics(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    public static GoogleAnalytics getInstance(Context context) {
        return zzaqc.zzbm(context).zzxi();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzur() {
        synchronized (GoogleAnalytics.class) {
            if (zzdou != null) {
                Iterator<Runnable> iterator = zzdou.iterator();
                while (iterator.hasNext()) {
                    iterator.next().run();
                }
                zzdou = null;
            }
            return;
        }
    }

    public final void dispatchLocalHits() {
        this.zzum().zzwx().zzwn();
    }

    @TargetApi(value=14)
    public final void enableAutoActivityReports(Application application) {
        if (!this.zzdow) {
            application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new zzb(this));
            this.zzdow = true;
        }
    }

    public final boolean getAppOptOut() {
        return this.zzdoy;
    }

    public final void initialize() {
        zzasm zzasm2 = this.zzum().zzwz();
        zzasm2.zzaai();
        if (zzasm2.zzaaj()) {
            this.setDryRun(zzasm2.zzaak());
        }
        zzasm2.zzaai();
        this.zzare = true;
    }

    public final boolean isDryRunEnabled() {
        return this.zzdox;
    }

    public final boolean isInitialized() {
        return this.zzare;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Tracker newTracker(int n) {
        synchronized (this) {
            zzask zzask2;
            Tracker tracker = new Tracker(this.zzum(), null, null);
            if (n > 0 && (zzask2 = (zzask)new zzasi(this.zzum()).zzav(n)) != null) {
                tracker.zza(zzask2);
            }
            tracker.initialize();
            return tracker;
        }
    }

    public final void reportActivityStart(Activity activity) {
        if (!this.zzdow) {
            this.zzj(activity);
        }
    }

    public final void reportActivityStop(Activity activity) {
        if (!this.zzdow) {
            this.zzk(activity);
        }
    }

    public final void setDryRun(boolean bl) {
        this.zzdox = bl;
    }

    final void zza(zza zza2) {
        this.zzdov.add(zza2);
        zza2 = this.zzum().getContext();
        if (zza2 instanceof Application) {
            this.enableAutoActivityReports((Application)zza2);
        }
    }

    final void zzb(zza zza2) {
        this.zzdov.remove(zza2);
    }

    final void zzj(Activity activity) {
        Iterator<zza> iterator = this.zzdov.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzl(activity);
        }
    }

    final void zzk(Activity activity) {
        Iterator<zza> iterator = this.zzdov.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzm(activity);
        }
    }

    static interface zza {
        public void zzl(Activity var1);

        public void zzm(Activity var1);
    }

    @TargetApi(value=14)
    final class zzb
    implements Application.ActivityLifecycleCallbacks {
        private /* synthetic */ GoogleAnalytics zzdpa;

        zzb(GoogleAnalytics googleAnalytics) {
            this.zzdpa = googleAnalytics;
        }

        public final void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public final void onActivityDestroyed(Activity activity) {
        }

        public final void onActivityPaused(Activity activity) {
        }

        public final void onActivityResumed(Activity activity) {
        }

        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public final void onActivityStarted(Activity activity) {
            this.zzdpa.zzj(activity);
        }

        public final void onActivityStopped(Activity activity) {
            this.zzdpa.zzk(activity);
        }
    }

}

