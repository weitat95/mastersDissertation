/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzat;
import java.util.List;

final class zzau
implements Runnable {
    private /* synthetic */ List zzkey;
    private /* synthetic */ long zzkez;
    private /* synthetic */ zzat zzkfa;

    zzau(zzat zzat2, List list, long l) {
        this.zzkfa = zzat2;
        this.zzkey = list;
        this.zzkez = l;
    }

    @Override
    public final void run() {
        zzat.zza(this.zzkfa, this.zzkey, this.zzkez);
    }
}

