/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.crashlytics.android.core;

import android.os.Looper;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

class CrashlyticsBackgroundWorker {
    private final ExecutorService executorService;

    public CrashlyticsBackgroundWorker(ExecutorService executorService) {
        this.executorService = executorService;
    }

    Future<?> submit(Runnable object) {
        try {
            object = this.executorService.submit(new Runnable((Runnable)object){
                final /* synthetic */ Runnable val$runnable;
                {
                    this.val$runnable = runnable;
                }

                @Override
                public void run() {
                    try {
                        this.val$runnable.run();
                        return;
                    }
                    catch (Exception exception) {
                        Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
                        return;
                    }
                }
            });
            return object;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }

    <T> Future<T> submit(Callable<T> object) {
        try {
            object = this.executorService.submit(new Callable<T>((Callable)object){
                final /* synthetic */ Callable val$callable;
                {
                    this.val$callable = callable;
                }

                @Override
                public T call() throws Exception {
                    Object v;
                    try {
                        v = this.val$callable.call();
                    }
                    catch (Exception exception) {
                        Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
                        return null;
                    }
                    return (T)v;
                }
            });
            return object;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }

    <T> T submitAndWait(Callable<T> callable) {
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                return this.executorService.submit(callable).get(4L, TimeUnit.SECONDS);
            }
            callable = this.executorService.submit(callable).get();
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", exception);
            return null;
        }
        return (T)callable;
    }

}

