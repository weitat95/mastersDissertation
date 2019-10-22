/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Binder
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzbhd;

public final class zzbhe {
    private Context mContext;

    public zzbhe(Context context) {
        this.mContext = context;
    }

    public final int checkCallingOrSelfPermission(String string2) {
        return this.mContext.checkCallingOrSelfPermission(string2);
    }

    public final int checkPermission(String string2, String string3) {
        return this.mContext.getPackageManager().checkPermission(string2, string3);
    }

    public final ApplicationInfo getApplicationInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationInfo(string2, n);
    }

    public final PackageInfo getPackageInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getPackageInfo(string2, n);
    }

    public final String[] getPackagesForUid(int n) {
        return this.mContext.getPackageManager().getPackagesForUid(n);
    }

    public final boolean zzamu() {
        String string2;
        if (Binder.getCallingUid() == Process.myUid()) {
            return zzbhd.zzcz(this.mContext);
        }
        if (zzq.isAtLeastO() && (string2 = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid())) != null) {
            return this.mContext.getPackageManager().isInstantApp(string2);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=19)
    public final boolean zzf(int n, String string2) {
        boolean bl = false;
        if (zzq.zzaml()) {
            try {
                ((AppOpsManager)this.mContext.getSystemService("appops")).checkPackage(n, string2);
                return true;
            }
            catch (SecurityException securityException) {
                return false;
            }
        }
        String[] arrstring = this.mContext.getPackageManager().getPackagesForUid(n);
        boolean bl2 = bl;
        if (string2 == null) return bl2;
        bl2 = bl;
        if (arrstring == null) return bl2;
        n = 0;
        do {
            bl2 = bl;
            if (n >= arrstring.length) return bl2;
            if (string2.equals(arrstring[n])) {
                return true;
            }
            ++n;
        } while (true);
    }

    public final CharSequence zzgt(String string2) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(string2, 0));
    }
}

