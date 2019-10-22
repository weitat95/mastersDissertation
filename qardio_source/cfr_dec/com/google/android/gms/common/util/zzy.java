/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.WorkSource
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.internal.zzbhf;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzy {
    private static final Method zzgew = zzy.zzamp();
    private static final Method zzgex = zzy.zzamq();
    private static final Method zzgey = zzy.zzamr();
    private static final Method zzgez = zzy.zzams();
    private static final Method zzgfa = zzy.zzamt();

    private static int zza(WorkSource workSource) {
        if (zzgey != null) {
            try {
                int n = (Integer)zzgey.invoke((Object)workSource, new Object[0]);
                return n;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return 0;
    }

    private static String zza(WorkSource object, int n) {
        if (zzgfa != null) {
            try {
                object = (String)zzgfa.invoke(object, n);
                return object;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static WorkSource zzaa(Context object, String string2) {
        block3: {
            if (object == null || object.getPackageManager() == null || string2 == null) {
                return null;
            }
            try {
                object = zzbhf.zzdb((Context)object).getApplicationInfo(string2, 0);
                if (object != null) break block3;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                String string3 = String.valueOf(string2);
                string3 = string3.length() != 0 ? "Could not find package: ".concat(string3) : new String("Could not find package: ");
                Log.e((String)"WorkSourceUtil", (String)string3);
                return null;
            }
            object = String.valueOf(string2);
            object = ((String)object).length() != 0 ? "Could not get applicationInfo from package: ".concat((String)object) : new String("Could not get applicationInfo from package: ");
            Log.e((String)"WorkSourceUtil", (String)object);
            return null;
        }
        return zzy.zze(((ApplicationInfo)object).uid, string2);
    }

    private static Method zzamp() {
        try {
            Method method = WorkSource.class.getMethod("add", Integer.TYPE);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzamq() {
        Method method = null;
        if (!zzq.zzamk()) return method;
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zzamr() {
        try {
            Method method = WorkSource.class.getMethod("size", new Class[0]);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zzams() {
        try {
            Method method = WorkSource.class.getMethod("get", Integer.TYPE);
            return method;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzamt() {
        Method method = null;
        if (!zzq.zzamk()) return method;
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List<String> zzb(WorkSource workSource) {
        int n = 0;
        int n2 = workSource == null ? 0 : zzy.zza(workSource);
        if (n2 == 0) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            Object object = arrayList;
            if (n >= n2) return object;
            object = zzy.zza(workSource, n);
            if (!zzu.zzgs((String)object)) {
                arrayList.add((String)object);
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzcy(Context context) {
        return context != null && context.getPackageManager() != null && zzbhf.zzdb(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) == 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static WorkSource zze(int n, String string2) {
        WorkSource workSource = new WorkSource();
        if (zzgex != null) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            try {
                zzgex.invoke((Object)workSource, n, string3);
                return workSource;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                return workSource;
            }
        } else {
            if (zzgew == null) return workSource;
            {
                try {
                    zzgew.invoke((Object)workSource, n);
                    return workSource;
                }
                catch (Exception exception) {
                    Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                    return workSource;
                }
            }
        }
    }
}

