/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

final class zzm
implements Executor {
    zzm() {
    }

    @Override
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}

