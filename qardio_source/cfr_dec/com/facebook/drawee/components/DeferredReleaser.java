/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.facebook.drawee.components;

import android.os.Handler;
import android.os.Looper;
import com.facebook.common.internal.Preconditions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeferredReleaser {
    private static DeferredReleaser sInstance = null;
    private final Set<Releasable> mPendingReleasables;
    private final Handler mUiHandler;
    private final Runnable releaseRunnable = new Runnable(){

        @Override
        public void run() {
            DeferredReleaser.ensureOnUiThread();
            Iterator iterator = DeferredReleaser.this.mPendingReleasables.iterator();
            while (iterator.hasNext()) {
                ((Releasable)iterator.next()).release();
            }
            DeferredReleaser.this.mPendingReleasables.clear();
        }
    };

    public DeferredReleaser() {
        this.mPendingReleasables = new HashSet<Releasable>();
        this.mUiHandler = new Handler(Looper.getMainLooper());
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void ensureOnUiThread() {
        boolean bl = Looper.getMainLooper().getThread() == Thread.currentThread();
        Preconditions.checkState(bl);
    }

    public static DeferredReleaser getInstance() {
        synchronized (DeferredReleaser.class) {
            if (sInstance == null) {
                sInstance = new DeferredReleaser();
            }
            DeferredReleaser deferredReleaser = sInstance;
            return deferredReleaser;
        }
    }

    public void cancelDeferredRelease(Releasable releasable) {
        DeferredReleaser.ensureOnUiThread();
        this.mPendingReleasables.remove(releasable);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void scheduleDeferredRelease(Releasable releasable) {
        DeferredReleaser.ensureOnUiThread();
        if (!this.mPendingReleasables.add(releasable) || this.mPendingReleasables.size() != 1) {
            return;
        }
        this.mUiHandler.post(this.releaseRunnable);
    }

    public static interface Releasable {
        public void release();
    }

}

