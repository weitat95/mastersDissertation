/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcir;
import java.util.List;
import java.util.concurrent.Callable;

final class zzcja
implements Callable<List<zzcgl>> {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcir zzjgo;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ String zzjgr;

    zzcja(zzcir zzcir2, String string2, String string3, String string4) {
        this.zzjgo = zzcir2;
        this.zzimf = string2;
        this.zzjgq = string3;
        this.zzjgr = string4;
    }

    @Override
    public final /* synthetic */ Object call() throws Exception {
        zzcir.zza(this.zzjgo).zzbal();
        return zzcir.zza(this.zzjgo).zzaws().zzh(this.zzimf, this.zzjgq, this.zzjgr);
    }
}

