/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

public class ThreadHandoffProducerQueue {
    private final Executor mExecutor;
    private boolean mQueueing = false;
    private final Deque<Runnable> mRunnableList;

    public ThreadHandoffProducerQueue(Executor executor) {
        this.mExecutor = Preconditions.checkNotNull(executor);
        this.mRunnableList = new ArrayDeque<Runnable>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addToQueueOrExecute(Runnable runnable) {
        synchronized (this) {
            if (this.mQueueing) {
                this.mRunnableList.add(runnable);
            } else {
                this.mExecutor.execute(runnable);
            }
            return;
        }
    }

    public void remove(Runnable runnable) {
        synchronized (this) {
            this.mRunnableList.remove(runnable);
            return;
        }
    }
}

