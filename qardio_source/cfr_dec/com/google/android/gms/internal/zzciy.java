/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcir;
import com.google.android.gms.internal.zzclp;
import java.util.List;
import java.util.concurrent.Callable;

final class zzciy
implements Callable<List<zzclp>> {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ String zzjgr;

    zzciy(zzcir zzcir2, String string2, String string3, String string4) {
        this.zzjgo = zzcir2;
        this.zzimf = string2;
        this.zzjgq = string3;
        this.zzjgr = string4;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcir.zza(this.zzjgo).zzbal();
        return zzcir.zza(this.zzjgo).zzaws().zzg(this.zzimf, this.zzjgq, this.zzjgr);
    }
}

