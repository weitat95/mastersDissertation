/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcir;
import java.util.List;
import java.util.concurrent.Callable;

final class zzciz
implements Callable<List<zzcgl>> {
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ String zzjgr;

    zzciz(zzcir zzcir2, zzcgi zzcgi2, String string2, String string3) {
        this.zzjgo = zzcir2;
        this.zzjgn = zzcgi2;
        this.zzjgq = string2;
        this.zzjgr = string3;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcir.zza(this.zzjgo).zzbal();
        return zzcir.zza(this.zzjgo).zzaws().zzh(this.zzjgn.packageName, this.zzjgq, this.zzjgr);
    }
}

