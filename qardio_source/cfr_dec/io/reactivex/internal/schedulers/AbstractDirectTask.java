/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

abstract class AbstractDirectTask
extends AtomicReference<Future<?>>
implements Disposable {
    protected static final FutureTask<Void> DISPOSED;
    protected static final FutureTask<Void> FINISHED;
    protected final Runnable runnable;
    protected Thread runner;

    static {
        FINISHED = new FutureTask<Object>(Functions.EMPTY_RUNNABLE, null);
        DISPOSED = new FutureTask<Object>(Functions.EMPTY_RUNNABLE, null);
    }

    AbstractDirectTask(Runnable runnable) {
        this.runnable = runnable;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void dispose() {
        Future future = (Future)this.get();
        if (future != FINISHED && future != DISPOSED && this.compareAndSet(future, DISPOSED) && future != null) {
            boolean bl = this.runner != Thread.currentThread();
            future.cancel(bl);
        }
    }

    @Override
    public final boolean isDisposed() {
        Future future = (Future)this.get();
        return future == FINISHED || future == DISPOSED;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setFuture(Future<?> future) {
        Future future2;
        do {
            if ((future2 = (Future)this.get()) == FINISHED) {
                return;
            }
            if (future2 != DISPOSED) continue;
            boolean bl = this.runner != Thread.currentThread();
            future.cancel(bl);
            return;
        } while (!this.compareAndSet(future2, future));
    }
}

