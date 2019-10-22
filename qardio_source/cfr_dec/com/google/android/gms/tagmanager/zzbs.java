/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.os.Build;
import com.google.android.gms.tagmanager.zzdj;
import java.io.File;

final class zzbs {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int version() {
        try {
            return Integer.parseInt(Build.VERSION.SDK);
        }
        catch (NumberFormatException numberFormatException) {
            String string2 = String.valueOf(Build.VERSION.SDK);
            string2 = string2.length() != 0 ? "Invalid version number: ".concat(string2) : new String("Invalid version number: ");
            zzdj.e(string2);
            return 0;
        }
    }

    @TargetApi(value=9)
    static boolean zzlo(String object) {
        if (zzbs.version() < 9) {
            return false;
        }
        object = new File((String)object);
        ((File)object).setReadable(false, false);
        ((File)object).setWritable(false, false);
        ((File)object).setReadable(true, true);
        ((File)object).setWritable(true, true);
        return true;
    }
}

