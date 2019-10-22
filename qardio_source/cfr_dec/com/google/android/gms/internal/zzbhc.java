/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.os.Process;

final class zzbhc
implements Runnable {
    private final int mPriority;
    private final Runnable zzz;

    public zzbhc(Runnable runnable, int n) {
        this.zzz = runnable;
        this.mPriority = n;
    }

    @Override
    public final void run() {
        Process.setThreadPriority((int)this.mPriority);
        this.zzz.run();
    }
}

