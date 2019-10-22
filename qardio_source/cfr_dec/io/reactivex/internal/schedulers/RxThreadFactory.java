/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.schedulers;

import io.reactivex.internal.schedulers.NonBlockingThread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class RxThreadFactory
extends AtomicLong
implements ThreadFactory {
    final boolean nonBlocking;
    final String prefix;
    final int priority;

    public RxThreadFactory(String string2) {
        this(string2, 5, false);
    }

    public RxThreadFactory(String string2, int n) {
        this(string2, n, false);
    }

    public RxThreadFactory(String string2, int n, boolean bl) {
        this.prefix = string2;
        this.priority = n;
        this.nonBlocking = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Thread newThread(Runnable runnable) {
        String string2 = this.prefix + '-' + this.incrementAndGet();
        runnable = this.nonBlocking ? new RxCustomThread(runnable, string2) : new Thread(runnable, string2);
        ((Thread)runnable).setPriority(this.priority);
        ((Thread)runnable).setDaemon(true);
        return runnable;
    }

    @Override
    public String toString() {
        return "RxThreadFactory[" + this.prefix + "]";
    }

    static final class RxCustomThread
    extends Thread
    implements NonBlockingThread {
        RxCustomThread(Runnable runnable, String string2) {
            super(runnable, string2);
        }
    }

}

