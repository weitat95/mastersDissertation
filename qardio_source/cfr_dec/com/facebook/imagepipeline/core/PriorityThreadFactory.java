/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.facebook.imagepipeline.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory
implements ThreadFactory {
    private final int mThreadPriority;

    public PriorityThreadFactory(int n) {
        this.mThreadPriority = n;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        return new Thread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                try {
                    Process.setThreadPriority((int)PriorityThreadFactory.this.mThreadPriority);
                }
                catch (Throwable throwable) {}
                runnable.run();
            }
        });
    }

}

