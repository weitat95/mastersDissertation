/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.PowerManager
 *  android.os.SystemClock
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzq;

public final class zzj {
    private static IntentFilter zzgel = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzgem;
    private static float zzgen;

    static {
        zzgen = Float.NaN;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=20)
    public static int zzcw(Context context) {
        int n = 1;
        if (context == null) return -1;
        if (context.getApplicationContext() == null) {
            return -1;
        }
        Intent intent = context.getApplicationContext().registerReceiver(null, zzgel);
        int n2 = intent == null ? 0 : intent.getIntExtra("plugged", 0);
        n2 = (n2 & 7) != 0 ? 1 : 0;
        if ((context = (PowerManager)context.getSystemService("power")) == null) {
            return -1;
        }
        boolean bl = zzq.zzamm() ? context.isInteractive() : context.isScreenOn();
        int n3 = bl ? 1 : 0;
        if (n2 != 0) {
            n2 = n;
            return n3 << 1 | n2;
        }
        n2 = 0;
        return n3 << 1 | n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float zzcx(Context context) {
        synchronized (zzj.class) {
            float f;
            if (SystemClock.elapsedRealtime() - zzgem < 60000L && !Float.isNaN(zzgen)) {
                f = zzgen;
                do {
                    return f;
                    break;
                } while (true);
            }
            if ((context = context.getApplicationContext().registerReceiver(null, zzgel)) != null) {
                int n = context.getIntExtra("level", -1);
                int n2 = context.getIntExtra("scale", -1);
                zzgen = (float)n / (float)n2;
            }
            zzgem = SystemClock.elapsedRealtime();
            f = zzgen;
            return f;
        }
    }
}

