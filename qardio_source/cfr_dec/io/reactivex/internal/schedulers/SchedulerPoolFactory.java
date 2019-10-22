/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SchedulerPoolFactory {
    static final Map<ScheduledThreadPoolExecutor, Object> POOLS;
    public static final boolean PURGE_ENABLED;
    public static final int PURGE_PERIOD_SECONDS;
    static final AtomicReference<ScheduledExecutorService> PURGE_THREAD;

    static {
        PURGE_THREAD = new AtomicReference();
        POOLS = new ConcurrentHashMap<ScheduledThreadPoolExecutor, Object>();
        boolean bl = true;
        int n = 1;
        Properties properties = System.getProperties();
        int n2 = n;
        if (properties.containsKey("rx2.purge-enabled")) {
            boolean bl2;
            bl = bl2 = Boolean.getBoolean("rx2.purge-enabled");
            n2 = n;
            if (bl2) {
                bl = bl2;
                n2 = n;
                if (properties.containsKey("rx2.purge-period-seconds")) {
                    n2 = Integer.getInteger("rx2.purge-period-seconds", 1);
                    bl = bl2;
                }
            }
        }
        PURGE_ENABLED = bl;
        PURGE_PERIOD_SECONDS = n2;
        SchedulerPoolFactory.start();
    }

    public static ScheduledExecutorService create(ThreadFactory object) {
        if ((object = Executors.newScheduledThreadPool(1, (ThreadFactory)object)) instanceof ScheduledThreadPoolExecutor) {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor)object;
            POOLS.put(scheduledThreadPoolExecutor, object);
        }
        return object;
    }

    public static void start() {
        ScheduledExecutorService scheduledExecutorService;
        while ((scheduledExecutorService = PURGE_THREAD.get()) == null || scheduledExecutorService.isShutdown()) {
            ScheduledExecutorService scheduledExecutorService2 = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (PURGE_THREAD.compareAndSet(scheduledExecutorService, scheduledExecutorService2)) {
                scheduledExecutorService2.scheduleAtFixedRate(new ScheduledTask(), PURGE_PERIOD_SECONDS, PURGE_PERIOD_SECONDS, TimeUnit.SECONDS);
                return;
            }
            scheduledExecutorService2.shutdownNow();
        }
        return;
    }

    static final class ScheduledTask
    implements Runnable {
        ScheduledTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                for (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor : new ArrayList<ScheduledThreadPoolExecutor>(POOLS.keySet())) {
                    if (scheduledThreadPoolExecutor.isShutdown()) {
                        POOLS.remove(scheduledThreadPoolExecutor);
                        continue;
                    }
                    scheduledThreadPoolExecutor.purge();
                }
                return;
            }
            catch (Throwable throwable) {
                RxJavaPlugins.onError(throwable);
            }
        }
    }

}

