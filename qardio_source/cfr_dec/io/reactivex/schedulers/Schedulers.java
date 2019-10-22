/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class Schedulers {
    static final Scheduler COMPUTATION;
    static final Scheduler IO;
    static final Scheduler NEW_THREAD;
    static final Scheduler SINGLE;
    static final Scheduler TRAMPOLINE;

    static {
        SINGLE = RxJavaPlugins.initSingleScheduler(new SingleTask());
        COMPUTATION = RxJavaPlugins.initComputationScheduler(new ComputationTask());
        IO = RxJavaPlugins.initIoScheduler(new IOTask());
        TRAMPOLINE = TrampolineScheduler.instance();
        NEW_THREAD = RxJavaPlugins.initNewThreadScheduler(new NewThreadTask());
    }

    public static Scheduler computation() {
        return RxJavaPlugins.onComputationScheduler(COMPUTATION);
    }

    public static Scheduler io() {
        return RxJavaPlugins.onIoScheduler(IO);
    }

    static final class ComputationHolder {
        static final Scheduler DEFAULT = new ComputationScheduler();
    }

    static final class ComputationTask
    implements Callable<Scheduler> {
        ComputationTask() {
        }

        @Override
        public Scheduler call() throws Exception {
            return ComputationHolder.DEFAULT;
        }
    }

    static final class IOTask
    implements Callable<Scheduler> {
        IOTask() {
        }

        @Override
        public Scheduler call() throws Exception {
            return IoHolder.DEFAULT;
        }
    }

    static final class IoHolder {
        static final Scheduler DEFAULT = new IoScheduler();
    }

    static final class NewThreadHolder {
        static final Scheduler DEFAULT = new NewThreadScheduler();
    }

    static final class NewThreadTask
    implements Callable<Scheduler> {
        NewThreadTask() {
        }

        @Override
        public Scheduler call() throws Exception {
            return NewThreadHolder.DEFAULT;
        }
    }

    static final class SingleHolder {
        static final Scheduler DEFAULT = new SingleScheduler();
    }

    static final class SingleTask
    implements Callable<Scheduler> {
        SingleTask() {
        }

        @Override
        public Scheduler call() throws Exception {
            return SingleHolder.DEFAULT;
        }
    }

}

