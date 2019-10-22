/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.zzm;
import java.util.concurrent.Executor;

public final class TaskExecutors {
    public static final Executor MAIN_THREAD = new zza();
    static final Executor zzkum = new zzm();

    static final class zza
    implements Executor {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public final void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

}

