/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgh;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import java.util.concurrent.Callable;

final class zzcio
implements Callable<String> {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcim zzjgh;

    zzcio(zzcim zzcim2, String string2) {
        this.zzjgh = zzcim2;
        this.zzimf = string2;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcgh zzcgh2 = this.zzjgh.zzaws().zzjb(this.zzimf);
        if (zzcgh2 == null) {
            this.zzjgh.zzawy().zzazf().log("App info was null when attempting to get app instance id");
            return null;
        }
        return zzcgh2.getAppInstanceId();
    }
}

