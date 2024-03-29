/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.android.plugins;

import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;

public final class RxAndroidPlugins {
    private static volatile Function<Callable<Scheduler>, Scheduler> onInitMainThreadHandler;
    private static volatile Function<Scheduler, Scheduler> onMainThreadHandler;

    static <T, R> R apply(Function<T, R> function, T t) {
        try {
            function = function.apply(t);
        }
        catch (Throwable throwable) {
            throw Exceptions.propagate(throwable);
        }
        return (R)function;
    }

    static Scheduler applyRequireNonNull(Function<Callable<Scheduler>, Scheduler> object, Callable<Scheduler> callable) {
        if ((object = RxAndroidPlugins.apply(object, callable)) == null) {
            throw new NullPointerException("Scheduler Callable returned null");
        }
        return object;
    }

    static Scheduler callRequireNonNull(Callable<Scheduler> object) {
        block3: {
            try {
                object = object.call();
                if (object != null) break block3;
            }
            catch (Throwable throwable) {
                throw Exceptions.propagate(throwable);
            }
            throw new NullPointerException("Scheduler Callable returned null");
        }
        return object;
    }

    public static Scheduler initMainThreadScheduler(Callable<Scheduler> callable) {
        if (callable == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Callable<Scheduler>, Scheduler> function = onInitMainThreadHandler;
        if (function == null) {
            return RxAndroidPlugins.callRequireNonNull(callable);
        }
        return RxAndroidPlugins.applyRequireNonNull(function, callable);
    }

    public static Scheduler onMainThreadScheduler(Scheduler scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function<Scheduler, Scheduler> function = onMainThreadHandler;
        if (function == null) {
            return scheduler;
        }
        return RxAndroidPlugins.apply(function, scheduler);
    }
}

