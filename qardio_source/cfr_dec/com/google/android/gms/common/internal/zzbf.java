/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.zzbhf;

public final class zzbf {
    private static Object sLock = new Object();
    private static boolean zzcls;
    private static String zzgbc;
    private static int zzgbd;

    public static String zzcp(Context context) {
        zzbf.zzcr(context);
        return zzgbc;
    }

    public static int zzcq(Context context) {
        zzbf.zzcr(context);
        return zzgbd;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zzcr(Context object) {
        Object object2 = sLock;
        synchronized (object2) {
            block7: {
                if (zzcls) {
                    return;
                }
                zzcls = true;
                String string2 = object.getPackageName();
                object = zzbhf.zzdb((Context)object);
                object = object.getApplicationInfo((String)string2, (int)128).metaData;
                if (object != null) break block7;
                return;
            }
            try {
                zzgbc = object.getString("com.google.app.id");
                zzgbd = object.getInt("com.google.android.gms.version");
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.wtf((String)"MetadataValueReader", (String)"This should never happen.", (Throwable)nameNotFoundException);
            }
            return;
        }
    }
}

