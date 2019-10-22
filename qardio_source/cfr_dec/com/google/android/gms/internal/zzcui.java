/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build;

public abstract class zzcui<T> {
    private static Context zzair;
    private static boolean zzcek;
    private static final Object zzjwy;
    private static Boolean zzjwz;

    static {
        zzjwy = new Object();
        zzair = null;
        zzcek = false;
        zzjwz = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzdz(Context context) {
        if (zzair == null) {
            Object object = zzjwy;
            synchronized (object) {
                Context context2;
                if (!(Build.VERSION.SDK_INT >= 24 && context.isDeviceProtectedStorage() || (context2 = context.getApplicationContext()) == null)) {
                    context = context2;
                }
                if (zzair != context) {
                    zzjwz = null;
                }
                zzair = context;
            }
            zzcek = false;
        }
    }
}

