/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.util.Log
 *  android.util.SparseArray
 */
package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

@Deprecated
public abstract class WakefulBroadcastReceiver
extends BroadcastReceiver {
    private static int mNextId;
    private static final SparseArray<PowerManager.WakeLock> sActiveWakeLocks;

    static {
        sActiveWakeLocks = new SparseArray();
        mNextId = 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean completeWakefulIntent(Intent sparseArray) {
        int n = sparseArray.getIntExtra("android.support.content.wakelockid", 0);
        if (n == 0) {
            return false;
        }
        sparseArray = sActiveWakeLocks;
        synchronized (sparseArray) {
            PowerManager.WakeLock wakeLock = (PowerManager.WakeLock)sActiveWakeLocks.get(n);
            if (wakeLock != null) {
                wakeLock.release();
                sActiveWakeLocks.remove(n);
                return true;
            }
            Log.w((String)"WakefulBroadcastReceiv.", (String)("No active wake lock id #" + n));
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ComponentName startWakefulService(Context context, Intent intent) {
        SparseArray<PowerManager.WakeLock> sparseArray = sActiveWakeLocks;
        synchronized (sparseArray) {
            int n = mNextId++;
            if (mNextId <= 0) {
                mNextId = 1;
            }
            intent.putExtra("android.support.content.wakelockid", n);
            intent = context.startService(intent);
            if (intent == null) {
                return null;
            }
            context = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "wake:" + intent.flattenToShortString());
            context.setReferenceCounted(false);
            context.acquire(60000L);
            sActiveWakeLocks.put(n, (Object)context);
            return intent;
        }
    }
}

