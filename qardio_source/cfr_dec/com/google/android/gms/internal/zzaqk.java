/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import com.google.android.gms.internal.zzaqg;
import com.google.android.gms.internal.zzaqi;

final class zzaqk
implements Runnable {
    private /* synthetic */ ComponentName val$name;
    private /* synthetic */ zzaqi zzduf;

    zzaqk(zzaqi zzaqi2, ComponentName componentName) {
        this.zzduf = zzaqi2;
        this.val$name = componentName;
    }

    @Override
    public final void run() {
        zzaqg.zza(this.zzduf.zzdub, this.val$name);
    }
}

