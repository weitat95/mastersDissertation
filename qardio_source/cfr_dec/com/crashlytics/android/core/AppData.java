/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.services.common.IdManager;

class AppData {
    public final String apiKey;
    public final String buildId;
    public final String installerPackageName;
    public final String packageName;
    public final String versionCode;
    public final String versionName;

    AppData(String string2, String string3, String string4, String string5, String string6, String string7) {
        this.apiKey = string2;
        this.buildId = string3;
        this.installerPackageName = string4;
        this.packageName = string5;
        this.versionCode = string6;
        this.versionName = string7;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AppData create(Context object, IdManager object2, String string2, String string3) throws PackageManager.NameNotFoundException {
        void var0_3;
        void var2_6;
        void var3_7;
        String string4;
        String string5 = object.getPackageName();
        string4 = ((IdManager)((Object)string4)).getInstallerPackageName();
        PackageInfo packageInfo = object.getPackageManager().getPackageInfo(string5, 0);
        String string6 = Integer.toString(packageInfo.versionCode);
        if (packageInfo.versionName == null) {
            return new AppData((String)var2_6, (String)var3_7, string4, string5, string6, (String)var0_3);
        }
        String string7 = packageInfo.versionName;
        return new AppData((String)var2_6, (String)var3_7, string4, string5, string6, (String)var0_3);
    }
}

