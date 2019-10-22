/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ScheduledRunnable
extends AtomicReferenceArray<Object>
implements Disposable,
Runnable,
Callable<Object> {
    static final Object DISPOSED = new Object();
    static final Object DONE = new Object();
    final Runnable actual;

    public ScheduledRunnable(Runnable runnable, DisposableContainer disposableContainer) {
        super(3);
        this.actual = runnable;
        this.lazySet(0, disposableContainer);
    }

    @Override
    public Object call() {
        this.run();
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void dispose() {
        Object e;
        Object e2;
        boolean bl = true;
        while ((e = this.get(1)) != DONE && e != DISPOSED) {
            if (!this.compareAndSet(1, e, DISPOSED)) continue;
            if (e == null) break;
            Future future = (Future)e;
            if (this.get(2) == Thread.currentThread()) {
                bl = false;
            }
            future.cancel(bl);
            break;
        }
        do {
            if ((e2 = this.get(0)) != DONE && e2 != DISPOSED && e2 != null) continue;
            return;
        } while (!this.compareAndSet(0, e2, DISPOSED));
        ((DisposableContainer)e2).delete(this);
    }

    @Override
    public boolean isDisposed() {
        Object e = this.get(1);
        return e == DISPOSED || e == DONE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.lazySet(2, Thread.currentThread());
        try {
            this.actual.run();
            do {
                return;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            try {
                RxJavaPlugins.onError(throwable);
                return;
            }
            catch (Throwable throwable2) {
                throw throwable2;
            }
            finally {
                this.lazySet(2, null);
                Object e = this.get(0);
                if (e != DISPOSED && e != null && this.compareAndSet(0, e, DONE)) {
                    ((DisposableContainer)e).delete(this);
                }
                while ((e = this.get(1)) != DISPOSED && !this.compareAndSet(1, e, DONE)) {
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFuture(Future<?> future) {
        Object e;
        boolean bl = true;
        do {
            if ((e = this.get(1)) == DONE) {
                return;
            }
            if (e != DISPOSED) continue;
            if (this.get(2) == Thread.currentThread()) {
                bl = false;
            }
            future.cancel(bl);
            return;
        } while (!this.compareAndSet(1, e, future));
    }
}

