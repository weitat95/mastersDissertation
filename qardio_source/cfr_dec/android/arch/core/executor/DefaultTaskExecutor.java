/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package android.arch.core.executor;

import android.arch.core.executor.TaskExecutor;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultTaskExecutor
extends TaskExecutor {
    private ExecutorService mDiskIO;
    private final Object mLock = new Object();
    private volatile Handler mMainHandler;

    public DefaultTaskExecutor() {
        this.mDiskIO = Executors.newFixedThreadPool(2);
    }

    @Override
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void postToMainThread(Runnable runnable) {
        if (this.mMainHandler == null) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mMainHandler == null) {
                    this.mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.mMainHandler.post(runnable);
    }
}

