/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.res.Configuration
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzk
implements Application.ActivityLifecycleCallbacks,
ComponentCallbacks2 {
    private static final zzk zzfog = new zzk();
    private boolean zzdtb = false;
    private final AtomicBoolean zzfoh = new AtomicBoolean();
    private final AtomicBoolean zzfoi = new AtomicBoolean();
    private final ArrayList<zzl> zzfoj = new ArrayList();

    private zzk() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zza(Application application) {
        zzk zzk2 = zzfog;
        synchronized (zzk2) {
            if (!zzk.zzfog.zzdtb) {
                application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)zzfog);
                application.registerComponentCallbacks((ComponentCallbacks)zzfog);
                zzk.zzfog.zzdtb = true;
            }
            return;
        }
    }

    public static zzk zzahb() {
        return zzfog;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbf(boolean bl) {
        zzk zzk2 = zzfog;
        synchronized (zzk2) {
            ArrayList<zzl> arrayList = this.zzfoj;
            int n = arrayList.size();
            int n2 = 0;
            while (n2 < n) {
                zzl zzl2 = arrayList.get(n2);
                ++n2;
                zzl2.zzbf(bl);
            }
            return;
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean bl = this.zzfoh.compareAndSet(true, false);
        this.zzfoi.set(true);
        if (bl) {
            this.zzbf(false);
        }
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean bl = this.zzfoh.compareAndSet(true, false);
        this.zzfoi.set(true);
        if (bl) {
            this.zzbf(false);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int n) {
        if (n == 20 && this.zzfoh.compareAndSet(false, true)) {
            this.zzfoi.set(true);
            this.zzbf(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(zzl zzl2) {
        zzk zzk2 = zzfog;
        synchronized (zzk2) {
            this.zzfoj.add(zzl2);
            return;
        }
    }

    @TargetApi(value=16)
    public final boolean zzbe(boolean bl) {
        block5: {
            block4: {
                bl = true;
                if (this.zzfoi.get()) break block4;
                if (!zzq.zzami()) break block5;
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                ActivityManager.getMyMemoryState((ActivityManager.RunningAppProcessInfo)runningAppProcessInfo);
                if (!this.zzfoi.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                    this.zzfoh.set(true);
                }
            }
            bl = this.zzfoh.get();
        }
        return bl;
    }
}

