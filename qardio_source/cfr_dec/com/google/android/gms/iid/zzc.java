/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.iid;

import android.content.Intent;
import com.google.android.gms.iid.zzb;

final class zzc
implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ Intent zzies;
    private /* synthetic */ zzb zziet;

    zzc(zzb zzb2, Intent intent, Intent intent2) {
        this.zziet = zzb2;
        this.val$intent = intent;
        this.zzies = intent2;
    }

    @Override
    public final void run() {
        this.zziet.handleIntent(this.val$intent);
        zzb.zza(this.zziet, this.zzies);
    }
}

