/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.Bundle
 */
package io.branch.referral;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

class BranchUtil {
    static boolean isCustomDebugEnabled_ = false;

    public static boolean isTestModeEnabled(Context context) {
        if (isCustomDebugEnabled_) {
            return isCustomDebugEnabled_;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null && applicationInfo.metaData.containsKey("io.branch.sdk.TestMode")) {
                return applicationInfo.metaData.getBoolean("io.branch.sdk.TestMode", false);
            }
            applicationInfo = context.getResources();
            boolean bl = Boolean.parseBoolean(applicationInfo.getString(applicationInfo.getIdentifier("io.branch.sdk.TestMode", "string", context.getPackageName())));
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

