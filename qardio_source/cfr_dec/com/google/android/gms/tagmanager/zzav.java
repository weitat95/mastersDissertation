/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzaq;
import com.google.android.gms.tagmanager.zzat;
import java.util.List;

final class zzav
implements Runnable {
    private /* synthetic */ zzat zzkfa;
    private /* synthetic */ zzaq zzkfb;

    zzav(zzat zzat2, zzaq zzaq2) {
        this.zzkfa = zzat2;
        this.zzkfb = zzaq2;
    }

    @Override
    public final void run() {
        this.zzkfb.zzak(zzat.zza(this.zzkfa));
    }
}

