/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcku;

final class zzckw
implements Runnable {
    private /* synthetic */ ComponentName val$name;
    private /* synthetic */ zzcku zzjit;

    zzckw(zzcku zzcku2, ComponentName componentName) {
        this.zzjit = zzcku2;
        this.val$name = componentName;
    }

    @Override
    public final void run() {
        zzckg.zza(this.zzjit.zzjij, this.val$name);
    }
}

