/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import java.io.File;

public final class zzv {
    @TargetApi(value=21)
    public static File getNoBackupFilesDir(Context context) {
        if (zzq.zzamn()) {
            return context.getNoBackupFilesDir();
        }
        return zzv.zzd(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static File zzd(File object) {
        synchronized (zzv.class) {
            Object object2 = object;
            if (((File)object).exists()) return object2;
            object2 = object;
            if (((File)object).mkdirs()) return object2;
            boolean bl = ((File)object).exists();
            if (bl) {
                return object;
            }
            object = ((String)(object = String.valueOf(((File)object).getPath()))).length() != 0 ? "Unable to create no-backup dir ".concat((String)object) : new String("Unable to create no-backup dir ");
            Log.w((String)"SupportV4Utils", (String)object);
            return null;
        }
    }
}

