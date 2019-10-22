/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.internal.schedulers.AbstractDirectTask;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class ScheduledDirectTask
extends AbstractDirectTask
implements Callable<Void> {
    public ScheduledDirectTask(Runnable runnable) {
        super(runnable);
    }

    @Override
    public Void call() throws Exception {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            return null;
        }
        finally {
            this.lazySet(FINISHED);
            this.runner = null;
        }
    }
}

