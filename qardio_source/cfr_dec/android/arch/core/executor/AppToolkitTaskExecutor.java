/*
 * Decompiled with CFR 0.147.
 */
package android.arch.core.executor;

import android.arch.core.executor.DefaultTaskExecutor;
import android.arch.core.executor.TaskExecutor;

public class AppToolkitTaskExecutor
extends TaskExecutor {
    private static volatile AppToolkitTaskExecutor sInstance;
    private TaskExecutor mDefaultTaskExecutor;
    private TaskExecutor mDelegate;

    private AppToolkitTaskExecutor() {
        this.mDelegate = this.mDefaultTaskExecutor = new DefaultTaskExecutor();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AppToolkitTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (AppToolkitTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new AppToolkitTaskExecutor();
            }
            return sInstance;
        }
    }

    @Override
    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        this.mDelegate.postToMainThread(runnable);
    }
}

