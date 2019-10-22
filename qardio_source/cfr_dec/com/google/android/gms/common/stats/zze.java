/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.stats.WakeLockEvent;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.util.zzj;
import java.util.List;

public final class zze {
    private static boolean zzgcy;
    private static zze zzgdy;
    private static Boolean zzgdz;

    static {
        zzgdy = new zze();
        zzgcy = false;
    }

    public static void zza(Context context, String string2, int n, String string3, String string4, String string5, int n2, List<String> list) {
        zze.zza(context, string2, 8, string3, string4, string5, n2, list, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zza(Context object, String object2, int n, String string2, String string3, String string4, int n2, List<String> object3, long l) {
        Object object4;
        long l2;
        block11: {
            block10: {
                if (zzgdz == null) {
                    zzgdz = false;
                }
                if (!zzgdz.booleanValue()) break block10;
                if (TextUtils.isEmpty((CharSequence)object2)) {
                    object = String.valueOf(object2);
                    object = ((String)object).length() != 0 ? "missing wakeLock key. ".concat((String)object) : new String("missing wakeLock key. ");
                    Log.e((String)"WakeLockTracker", (String)object);
                    return;
                }
                l2 = System.currentTimeMillis();
                if (7 == n || 8 == n || 10 == n || 11 == n) break block11;
            }
            return;
        }
        if (object3 != null && object3.size() == 1) {
            object4 = object3;
            if ("com.google.android.gms".equals(object3.get(0))) {
                object4 = null;
            }
            object3 = object4;
        }
        long l3 = SystemClock.elapsedRealtime();
        int n3 = zzj.zzcw((Context)object);
        String string5 = object.getPackageName();
        object4 = string5;
        if ("com.google.android.gms".equals(string5)) {
            object4 = null;
        }
        object2 = new WakeLockEvent(l2, n, string2, n2, (List<String>)object3, (String)object2, l3, n3, string3, (String)object4, zzj.zzcx((Context)object), l, string4);
        try {
            object.startService(new Intent().setComponent(zzb.zzgdd).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", (Parcelable)object2));
            return;
        }
        catch (Exception exception) {
            Log.wtf((String)"WakeLockTracker", (Throwable)exception);
            return;
        }
    }

    public static zze zzamf() {
        return zzgdy;
    }
}

