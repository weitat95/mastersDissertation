/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public final class zzbha
implements Executor {
    private final Handler mHandler;

    public zzbha(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    @Override
    public final void execute(Runnable runnable) {
        this.mHandler.post(runnable);
    }
}

