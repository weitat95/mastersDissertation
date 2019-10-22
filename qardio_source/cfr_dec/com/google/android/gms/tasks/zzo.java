/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzn;
import java.util.concurrent.Callable;

final class zzo
implements Runnable {
    private /* synthetic */ Callable val$callable;
    private /* synthetic */ zzn zzkur;

    zzo(zzn zzn2, Callable callable) {
        this.zzkur = zzn2;
        this.val$callable = callable;
    }

    @Override
    public final void run() {
        try {
            this.zzkur.setResult(this.val$callable.call());
            return;
        }
        catch (Exception exception) {
            this.zzkur.setException(exception);
            return;
        }
    }
}

