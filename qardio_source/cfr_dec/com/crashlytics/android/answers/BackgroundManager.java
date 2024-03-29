/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class BackgroundManager {
    final AtomicReference<ScheduledFuture<?>> backgroundFutureRef;
    private final ScheduledExecutorService executorService;
    private volatile boolean flushOnBackground = true;
    boolean inBackground = true;
    private final List<Listener> listeners = new ArrayList<Listener>();

    public BackgroundManager(ScheduledExecutorService scheduledExecutorService) {
        this.backgroundFutureRef = new AtomicReference();
        this.executorService = scheduledExecutorService;
    }

    private void notifyBackground() {
        Iterator<Listener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onBackground();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onActivityPaused() {
        if (!this.flushOnBackground || this.inBackground) return;
        this.inBackground = true;
        try {
            this.backgroundFutureRef.compareAndSet(null, this.executorService.schedule(new Runnable(){

                @Override
                public void run() {
                    BackgroundManager.this.backgroundFutureRef.set(null);
                    BackgroundManager.this.notifyBackground();
                }
            }, 5000L, TimeUnit.MILLISECONDS));
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Fabric.getLogger().d("Answers", "Failed to schedule background detector", rejectedExecutionException);
            return;
        }
    }

    public void onActivityResumed() {
        this.inBackground = false;
        ScheduledFuture scheduledFuture = this.backgroundFutureRef.getAndSet(null);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    public void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void setFlushOnBackground(boolean bl) {
        this.flushOnBackground = bl;
    }

    public static interface Listener {
        public void onBackground();
    }

}

