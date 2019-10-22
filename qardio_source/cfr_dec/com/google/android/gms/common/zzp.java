/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageInstaller
 *  android.content.pm.PackageInstaller$SessionInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.UserManager
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.common.zzf;
import com.google.android.gms.common.zzh;
import com.google.android.gms.common.zzk;
import com.google.android.gms.common.zzq;
import com.google.android.gms.internal.zzbhf;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class zzp {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 11910000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean zzflj = false;
    private static boolean zzflk = false;
    private static boolean zzfll = false;
    private static boolean zzflm = false;
    static final AtomicBoolean zzfln = new AtomicBoolean();
    private static final AtomicBoolean zzflo = new AtomicBoolean();

    zzp() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n, Context context, int n2) {
        return zzf.zzafy().getErrorResolutionPendingIntent(context, n, n2);
    }

    @Deprecated
    public static String getErrorString(int n) {
        return ConnectionResult.getStatusString(n);
    }

    public static Context getRemoteContext(Context context) {
        try {
            context = context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            context = context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context object) {
        PackageManager packageManager;
        PackageInfo packageInfo2;
        PackageInfo packageInfo;
        int n;
        void var0_7;
        block19: {
            block17: {
                zzh zzh2;
                block18: {
                    packageManager = object.getPackageManager();
                    try {
                        object.getResources().getString(R.string.common_google_play_services_unknown_issue);
                    }
                    catch (Throwable throwable) {
                        Log.e((String)"GooglePlayServicesUtil", (String)"The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
                    }
                    if (!GOOGLE_PLAY_SERVICES_PACKAGE.equals(object.getPackageName()) && !zzflo.get()) {
                        n = zzbf.zzcq((Context)object);
                        if (n == 0) {
                            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
                        }
                        if (n != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                            int n2 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                            throw new IllegalStateException(new StringBuilder(String.valueOf("com.google.android.gms.version").length() + 290).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ").append(n2).append(" but found ").append(n).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"").append("com.google.android.gms.version").append("\" android:value=\"@integer/google_play_services_version\" />").toString());
                        }
                    }
                    n = !zzi.zzct((Context)object) && !zzi.zzcv((Context)object) ? 1 : 0;
                    packageInfo = null;
                    if (n != 0) {
                        packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 8256);
                    }
                    try {
                        packageInfo2 = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
                        return 1;
                    }
                    zzq.zzci((Context)object);
                    if (n == 0) break block17;
                    zzh2 = zzq.zza(packageInfo, zzk.zzflf);
                    if (zzh2 == null) {
                        Log.w((String)"GooglePlayServicesUtil", (String)"Google Play Store signature invalid.");
                        return 9;
                    }
                    break block18;
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        Log.w((String)"GooglePlayServicesUtil", (String)"Google Play Store is missing.");
                        return 9;
                    }
                }
                if (zzq.zza(packageInfo2, zzh2) == null) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services signature invalid.");
                    return 9;
                }
                break block19;
            }
            if (zzq.zza(packageInfo2, zzk.zzflf) == null) {
                Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services signature invalid.");
                return 9;
            }
        }
        if (packageInfo2.versionCode / 1000 < (n = GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000)) {
            n = GOOGLE_PLAY_SERVICES_VERSION_CODE;
            int n3 = packageInfo2.versionCode;
            Log.w((String)"GooglePlayServicesUtil", (String)new StringBuilder(77).append("Google Play services out of date.  Requires ").append(n).append(" but found ").append(n3).toString());
            return 2;
        }
        PackageInfo packageInfo3 = packageInfo = packageInfo2.applicationInfo;
        if (packageInfo == null) {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
        }
        if (var0_7.enabled) return 0;
        return 3;
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.wtf((String)"GooglePlayServicesUtil", (String)"Google Play services missing when getting application info.", (Throwable)nameNotFoundException);
            return 1;
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 9: 
        }
        return true;
    }

    @Deprecated
    @TargetApi(value=19)
    public static boolean zzb(Context context, int n, String string2) {
        return zzx.zzb(context, n, string2);
    }

    @Deprecated
    public static void zzbp(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int n = zzf.zzafy().isGooglePlayServicesAvailable(context);
        if (n != 0) {
            zzf.zzafy();
            context = zzf.zza(context, n, "e");
            Log.e((String)"GooglePlayServicesUtil", (String)new StringBuilder(57).append("GooglePlayServices not available due to error ").append(n).toString());
            if (context == null) {
                throw new GooglePlayServicesNotAvailableException(n);
            }
            throw new GooglePlayServicesRepairableException(n, "Google Play Services not available", (Intent)context);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static void zzce(Context context) {
        if (zzfln.getAndSet(true)) return;
        {
            try {
                if ((context = (NotificationManager)context.getSystemService("notification")) == null) return;
                {
                    context.cancel(10436);
                    return;
                }
            }
            catch (SecurityException securityException) {
                return;
            }
        }
    }

    @Deprecated
    public static int zzcf(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            return context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
            return 0;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean zzch(Context var0) {
        block7: {
            var1_3 = false;
            if (zzp.zzflm) break block7;
            try {
                var2_4 = zzbhf.zzdb(var0).getPackageInfo("com.google.android.gms", 64);
                if (var2_4 == null) ** GOTO lbl-1000
                zzq.zzci(var0);
                if (zzq.zza(var2_4, new zzh[]{zzk.zzflf[1]}) != null) {
                    zzp.zzfll = true;
                } else lbl-1000:
                // 2 sources
                {
                    zzp.zzfll = false;
                }
                zzp.zzflm = true;
            }
            catch (PackageManager.NameNotFoundException var0_1) {
                Log.w((String)"GooglePlayServicesUtil", (String)"Cannot find Google Play services package name.", (Throwable)var0_1);
                zzp.zzflm = true;
            }
        }
        if (zzp.zzfll != false) return true;
        if ("user".equals(Build.TYPE) != false) return var1_3;
        return true;
        catch (Throwable var0_2) {
            zzp.zzflm = true;
            throw var0_2;
        }
    }

    @Deprecated
    public static boolean zze(Context context, int n) {
        if (n == 18) {
            return true;
        }
        if (n == 1) {
            return zzp.zzv(context, GOOGLE_PLAY_SERVICES_PACKAGE);
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int n) {
        return zzx.zzf(context, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=21)
    static boolean zzv(Context context, String string2) {
        Object object;
        boolean bl = string2.equals(GOOGLE_PLAY_SERVICES_PACKAGE);
        if (com.google.android.gms.common.util.zzq.zzamn()) {
            try {
                object = context.getPackageManager().getPackageInstaller().getAllSessions();
                object = object.iterator();
            }
            catch (Exception exception) {
                return false;
            }
            while (object.hasNext()) {
                if (!string2.equals(((PackageInstaller.SessionInfo)object.next()).getAppPackageName())) continue;
                return true;
            }
        }
        object = context.getPackageManager();
        try {
            string2 = object.getApplicationInfo(string2, 8192);
            if (bl) {
                return ((ApplicationInfo)string2).enabled;
            }
            if (!((ApplicationInfo)string2).enabled) return false;
            if (!com.google.android.gms.common.util.zzq.zzamk()) return true;
            if ((context = ((UserManager)context.getSystemService("user")).getApplicationRestrictions(context.getPackageName())) == null) return true;
            bl = "true".equals(context.getString("restricted_profile"));
            if (!bl) return true;
            return false;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }
}

