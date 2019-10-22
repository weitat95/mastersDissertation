/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzbhe;

public final class zzbhf {
    private static zzbhf zzgfh = new zzbhf();
    private zzbhe zzgfg = null;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final zzbhe zzda(Context object) {
        synchronized (this) {
            void var1_2;
            if (this.zzgfg != null) return this.zzgfg;
            if (object.getApplicationContext() != null) {
                Context context = object.getApplicationContext();
            }
            this.zzgfg = new zzbhe((Context)var1_2);
            return this.zzgfg;
        }
    }

    public static zzbhe zzdb(Context context) {
        return zzgfh.zzda(context);
    }
}

