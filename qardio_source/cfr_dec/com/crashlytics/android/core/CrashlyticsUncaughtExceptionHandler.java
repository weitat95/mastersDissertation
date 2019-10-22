/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import java.util.concurrent.atomic.AtomicBoolean;

class CrashlyticsUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final CrashListener crashListener;
    private final Thread.UncaughtExceptionHandler defaultHandler;
    private final AtomicBoolean isHandlingException;

    public CrashlyticsUncaughtExceptionHandler(CrashListener crashListener, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.crashListener = crashListener;
        this.defaultHandler = uncaughtExceptionHandler;
        this.isHandlingException = new AtomicBoolean(false);
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        this.isHandlingException.set(true);
        try {
            this.crashListener.onUncaughtException(thread, throwable);
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the uncaught exception handler", exception);
            return;
        }
        finally {
            Fabric.getLogger().d("CrashlyticsCore", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, throwable);
            this.isHandlingException.set(false);
        }
    }

    static interface CrashListener {
        public void onUncaughtException(Thread var1, Throwable var2);
    }

}

