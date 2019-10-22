/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcir;
import com.google.android.gms.internal.zzclp;
import java.util.List;
import java.util.concurrent.Callable;

final class zzcjh
implements Callable<List<zzclp>> {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcir zzjgo;

    zzcjh(zzcir zzcir2, zzcgi zzcgi2) {
        this.zzjgo = zzcir2;
        this.zzjgn = zzcgi2;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcir.zza(this.zzjgo).zzbal();
        return zzcir.zza(this.zzjgo).zzaws().zzja(this.zzjgn.packageName);
    }
}

