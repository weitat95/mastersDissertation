/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.util.zzc;
import java.util.Collections;
import java.util.List;

public final class zza {
    private static final Object zzgai = new Object();
    private static volatile zza zzgcx;
    private static boolean zzgcy;
    private final List<String> zzgcz = Collections.EMPTY_LIST;
    private final List<String> zzgda = Collections.EMPTY_LIST;
    private final List<String> zzgdb = Collections.EMPTY_LIST;
    private final List<String> zzgdc = Collections.EMPTY_LIST;

    static {
        zzgcy = false;
    }

    private zza() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zza zzamc() {
        if (zzgcx == null) {
            Object object = zzgai;
            synchronized (object) {
                if (zzgcx == null) {
                    zzgcx = new zza();
                }
            }
        }
        return zzgcx;
    }

    public final boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int n) {
        return this.zza(context, context.getClass().getName(), intent, serviceConnection, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean zza(Context context, String string2, Intent intent, ServiceConnection serviceConnection, int n) {
        string2 = intent.getComponent();
        boolean bl = string2 == null ? false : zzc.zzz(context, string2.getPackageName());
        if (bl) {
            Log.w((String)"ConnectionTracker", (String)"Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        return context.bindService(intent, serviceConnection, n);
    }
}

