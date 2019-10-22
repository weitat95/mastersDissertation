/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzcir;
import java.util.concurrent.Callable;

final class zzcje
implements Callable<byte[]> {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ zzcha zzjgs;

    zzcje(zzcir zzcir2, zzcha zzcha2, String string2) {
        this.zzjgo = zzcir2;
        this.zzjgs = zzcha2;
        this.zzimf = string2;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcir.zza(this.zzjgo).zzbal();
        return zzcir.zza(this.zzjgo).zza(this.zzjgs, this.zzimf);
    }
}

