/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbhc;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbhb
implements ThreadFactory {
    private final int mPriority;
    private final String zzgfb;
    private final AtomicInteger zzgfc = new AtomicInteger();
    private final ThreadFactory zzgfd = Executors.defaultThreadFactory();

    public zzbhb(String string2) {
        this(string2, 0);
    }

    private zzbhb(String string2, int n) {
        this.zzgfb = zzbq.checkNotNull(string2, "Name must not be null");
        this.mPriority = 0;
    }

    @Override
    public final Thread newThread(Runnable runnable) {
        runnable = this.zzgfd.newThread(new zzbhc(runnable, 0));
        String string2 = this.zzgfb;
        int n = this.zzgfc.getAndIncrement();
        ((Thread)runnable).setName(new StringBuilder(String.valueOf(string2).length() + 13).append(string2).append("[").append(n).append("]").toString());
        return runnable;
    }
}

