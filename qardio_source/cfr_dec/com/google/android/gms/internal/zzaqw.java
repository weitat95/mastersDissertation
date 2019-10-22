/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaqu;
import java.util.concurrent.Callable;

final class zzaqw
implements Callable<String> {
    private /* synthetic */ zzaqu zzdvf;

    zzaqw(zzaqu zzaqu2) {
        this.zzdvf = zzaqu2;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        return zzaqu.zza(this.zzdvf);
    }
}

