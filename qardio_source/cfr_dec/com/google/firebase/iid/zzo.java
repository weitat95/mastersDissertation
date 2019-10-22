/*
 * Decompiled with CFR 0.147.
 */
package com.google.firebase.iid;

import com.google.firebase.iid.zzk;
import com.google.firebase.iid.zzr;

final class zzo
implements Runnable {
    private final zzk zznzg;
    private final zzr zznzh;

    zzo(zzk zzk2, zzr zzr2) {
        this.zznzg = zzk2;
        this.zznzh = zzr2;
    }

    @Override
    public final void run() {
        this.zznzg.zzic(this.zznzh.zzift);
    }
}

