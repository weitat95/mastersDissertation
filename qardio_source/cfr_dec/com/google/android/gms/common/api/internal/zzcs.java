/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzbhb;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class zzcs {
    private static final ExecutorService zzfsx = new ThreadPoolExecutor(0, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new zzbhb("GAC_Transform"));

    public static ExecutorService zzaip() {
        return zzfsx;
    }
}

