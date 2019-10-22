/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.PermissionInfo
 *  android.content.pm.ResolveInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import com.mixpanel.android.takeoverinapp.TakeoverInAppActivity;
import com.mixpanel.android.util.MPLog;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ConfigurationChecker {
    public static String LOGTAG = "MixpanelAPI.ConfigurationChecker";
    private static Boolean mTakeoverActivityAvailable;

    public static boolean checkBasicConfiguration(Context object) {
        PackageManager packageManager = object.getPackageManager();
        object = object.getPackageName();
        if (packageManager == null || object == null) {
            MPLog.w(LOGTAG, "Can't check configuration when using a Context with null packageManager or packageName");
            return false;
        }
        if (packageManager.checkPermission("android.permission.INTERNET", (String)object) != 0) {
            MPLog.w(LOGTAG, "Package does not have permission android.permission.INTERNET - Mixpanel will not work at all!");
            MPLog.i(LOGTAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"android.permission.INTERNET\" />");
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean checkPushConfiguration(Context context) {
        ActivityInfo[] arractivityInfo = context.getPackageManager();
        String string2 = context.getPackageName();
        if (arractivityInfo == null || string2 == null) {
            MPLog.w(LOGTAG, "Can't check configuration when using a Context with null packageManager or packageName");
            return false;
        }
        Object object = string2 + ".permission.C2D_MESSAGE";
        try {
            arractivityInfo.getPermissionInfo((String)object, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MPLog.w(LOGTAG, "Application does not define permission " + (String)object);
            MPLog.i(LOGTAG, "You will need to add the following lines to your application manifest:\n<permission android:name=\"" + string2 + ".permission.C2D_MESSAGE\" android:protectionLevel=\"signature\" />\n<uses-permission android:name=\"" + string2 + ".permission.C2D_MESSAGE\" />");
            return false;
        }
        if (arractivityInfo.checkPermission("com.google.android.c2dm.permission.RECEIVE", string2) != 0) {
            MPLog.w(LOGTAG, "Package does not have permission com.google.android.c2dm.permission.RECEIVE");
            MPLog.i(LOGTAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"com.google.android.c2dm.permission.RECEIVE\" />");
            return false;
        }
        if (arractivityInfo.checkPermission("android.permission.INTERNET", string2) != 0) {
            MPLog.w(LOGTAG, "Package does not have permission android.permission.INTERNET");
            MPLog.i(LOGTAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"android.permission.INTERNET\" />");
            return false;
        }
        if (arractivityInfo.checkPermission("android.permission.WAKE_LOCK", string2) != 0) {
            MPLog.w(LOGTAG, "Package does not have permission android.permission.WAKE_LOCK");
            MPLog.i(LOGTAG, "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"android.permission.WAKE_LOCK\" />");
            return false;
        }
        if (arractivityInfo.checkPermission("android.permission.GET_ACCOUNTS", string2) != 0) {
            MPLog.i(LOGTAG, "Package does not have permission android.permission.GET_ACCOUNTS");
            MPLog.i(LOGTAG, "Android versions below 4.1 require GET_ACCOUNTS to receive Mixpanel push notifications.\nDevices with later OS versions will still be able to receive messages, but if you'd like to support older devices, you'll need to add the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"android.permission.GET_ACCOUNTS\" />");
            if (Build.VERSION.SDK_INT < 16) return false;
        }
        try {
            object = arractivityInfo.getPackageInfo(string2, 2);
            arractivityInfo = ((PackageInfo)object).receivers;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MPLog.w(LOGTAG, "Could not get receivers for package " + string2);
            return false;
        }
        if (arractivityInfo == null || arractivityInfo.length == 0) {
            MPLog.w(LOGTAG, "No receiver for package " + string2);
            MPLog.i(LOGTAG, "You can fix this with the following into your <application> tag:\n" + ConfigurationChecker.samplePushConfigurationMessage(string2));
            return false;
        }
        object = new HashSet();
        for (ActivityInfo activityInfo : arractivityInfo) {
            if (!"com.google.android.c2dm.permission.SEND".equals(activityInfo.permission)) continue;
            object.add(activityInfo.name);
        }
        if (object.isEmpty()) {
            MPLog.w(LOGTAG, "No receiver allowed to receive com.google.android.c2dm.permission.SEND");
            MPLog.i(LOGTAG, "You can fix by pasting the following into the <application> tag in your AndroidManifest.xml:\n" + ConfigurationChecker.samplePushConfigurationMessage(string2));
            return false;
        }
        if (!ConfigurationChecker.checkReceiver(context, (Set<String>)object, "com.google.android.c2dm.intent.RECEIVE")) return false;
        int n = 0;
        try {
            Class.forName("com.google.android.gms.common.GooglePlayServicesUtil");
            n = 1;
        }
        catch (ClassNotFoundException classNotFoundException) {
            MPLog.w(LOGTAG, "Google Play Services aren't included in your build- push notifications won't work on Lollipop/API 21 or greater");
            MPLog.i(LOGTAG, "You can fix this by adding com.google.android.gms:play-services as a dependency of your gradle or maven project");
        }
        int n2 = 1;
        if (!ConfigurationChecker.checkReceiver(context, (Set<String>)object, "com.google.android.c2dm.intent.REGISTRATION")) {
            MPLog.i(LOGTAG, "(You can still receive push notifications on Lollipop/API 21 or greater with this configuration)");
            n2 = 0;
        }
        if (n == 0 && n2 == 0) return false;
        return true;
    }

    private static boolean checkReceiver(Context object, Set<String> set, String string2) {
        Object object2 = object.getPackageManager();
        object = object.getPackageName();
        Intent intent = new Intent(string2);
        intent.setPackage((String)object);
        object2 = object2.queryBroadcastReceivers(intent, 128);
        if (object2.isEmpty()) {
            MPLog.w(LOGTAG, "No receivers for action " + string2);
            MPLog.i(LOGTAG, "You can fix by pasting the following into the <application> tag in your AndroidManifest.xml:\n" + ConfigurationChecker.samplePushConfigurationMessage((String)object));
            return false;
        }
        object = object2.iterator();
        while (object.hasNext()) {
            string2 = ((ResolveInfo)object.next()).activityInfo.name;
            if (set.contains(string2)) continue;
            MPLog.w(LOGTAG, "Receiver " + string2 + " is not set with permission com.google.android.c2dm.permission.SEND");
            MPLog.i(LOGTAG, "Please add the attribute 'android:permission=\"com.google.android.c2dm.permission.SEND\"' to your <receiver> tag");
            return false;
        }
        return true;
    }

    public static boolean checkTakeoverInAppActivityAvailable(Context context) {
        if (mTakeoverActivityAvailable == null) {
            if (Build.VERSION.SDK_INT < 16) {
                mTakeoverActivityAvailable = false;
                return mTakeoverActivityAvailable;
            }
            Intent intent = new Intent(context, TakeoverInAppActivity.class);
            intent.addFlags(268435456);
            intent.addFlags(131072);
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
                MPLog.w(LOGTAG, TakeoverInAppActivity.class.getName() + " is not registered as an activity in your application, so takeover in-apps can't be shown.");
                MPLog.i(LOGTAG, "Please add the child tag <activity android:name=\"com.mixpanel.android.takeoverinapp.TakeoverInAppActivity\" /> to your <application> tag.");
                mTakeoverActivityAvailable = false;
                return mTakeoverActivityAvailable;
            }
            mTakeoverActivityAvailable = true;
        }
        return mTakeoverActivityAvailable;
    }

    private static String samplePushConfigurationMessage(String string2) {
        return "<receiver android:name=\"com.mixpanel.android.mpmetrics.GCMReceiver\"\n          android:permission=\"com.google.android.c2dm.permission.SEND\" >\n    <intent-filter>\n       <action android:name=\"com.google.android.c2dm.intent.RECEIVE\" />\n       <action android:name=\"com.google.android.c2dm.intent.REGISTRATION\" />\n       <category android:name=\"" + string2 + "\" />\n    </intent-filter>\n</receiver>";
    }
}

