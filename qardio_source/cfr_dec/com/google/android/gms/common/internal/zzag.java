/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ServiceConnection
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.ServiceConnection;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzai;

public abstract class zzag {
    private static final Object zzgai = new Object();
    private static zzag zzgaj;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzag zzco(Context context) {
        Object object = zzgai;
        synchronized (object) {
            if (zzgaj == null) {
                zzgaj = new zzai(context.getApplicationContext());
            }
            return zzgaj;
        }
    }

    public final void zza(String string2, String string3, int n, ServiceConnection serviceConnection, String string4) {
        this.zzb(new zzah(string2, string3, n), serviceConnection, string4);
    }

    protected abstract boolean zza(zzah var1, ServiceConnection var2, String var3);

    protected abstract void zzb(zzah var1, ServiceConnection var2, String var3);
}

