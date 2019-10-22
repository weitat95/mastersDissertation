/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.facebook.common.executors;

import android.os.Handler;
import android.os.Looper;
import com.facebook.common.executors.HandlerExecutorServiceImpl;

public class UiThreadImmediateExecutorService
extends HandlerExecutorServiceImpl {
    private static UiThreadImmediateExecutorService sInstance = null;

    private UiThreadImmediateExecutorService() {
        super(new Handler(Looper.getMainLooper()));
    }

    public static UiThreadImmediateExecutorService getInstance() {
        if (sInstance == null) {
            sInstance = new UiThreadImmediateExecutorService();
        }
        return sInstance;
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isHandlerThread()) {
            runnable.run();
            return;
        }
        super.execute(runnable);
    }
}

