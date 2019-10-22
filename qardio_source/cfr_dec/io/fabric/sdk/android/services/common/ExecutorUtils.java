/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {
    private static final void addDelayedShutdownHook(String string2, ExecutorService executorService) {
        ExecutorUtils.addDelayedShutdownHook(string2, executorService, 2L, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(final String string2, final ExecutorService executorService, final long l, final TimeUnit timeUnit) {
        Runtime.getRuntime().addShutdownHook(new Thread((Runnable)new BackgroundPriorityRunnable(){

            @Override
            public void onRun() {
                try {
                    Fabric.getLogger().d("Fabric", "Executing shutdown hook for " + string2);
                    executorService.shutdown();
                    if (!executorService.awaitTermination(l, timeUnit)) {
                        Fabric.getLogger().d("Fabric", string2 + " did not shut down in the" + " allocated time. Requesting immediate shutdown.");
                        executorService.shutdownNow();
                    }
                    return;
                }
                catch (InterruptedException interruptedException) {
                    Fabric.getLogger().d("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", string2));
                    executorService.shutdownNow();
                    return;
                }
            }
        }, "Crashlytics Shutdown Hook for " + string2));
    }

    public static ExecutorService buildSingleThreadExecutorService(String string2) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(ExecutorUtils.getNamedThreadFactory(string2));
        ExecutorUtils.addDelayedShutdownHook(string2, executorService);
        return executorService;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String string2) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(ExecutorUtils.getNamedThreadFactory(string2));
        ExecutorUtils.addDelayedShutdownHook(string2, scheduledExecutorService);
        return scheduledExecutorService;
    }

    public static final ThreadFactory getNamedThreadFactory(final String string2) {
        return new ThreadFactory(new AtomicLong(1L)){
            final /* synthetic */ AtomicLong val$count;
            {
                this.val$count = atomicLong;
            }

            @Override
            public Thread newThread(final Runnable runnable) {
                runnable = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable(){

                    @Override
                    public void onRun() {
                        runnable.run();
                    }
                });
                ((Thread)runnable).setName(string2 + this.val$count.getAndIncrement());
                return runnable;
            }

        };
    }

}

