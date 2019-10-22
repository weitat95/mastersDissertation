/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    private NavUtils() {
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Object object;
        if (Build.VERSION.SDK_INT >= 16 && (object = activity.getParentActivityIntent()) != null) {
            return object;
        }
        object = NavUtils.getParentActivityName(activity);
        if (object == null) {
            return null;
        }
        ComponentName componentName = new ComponentName((Context)activity, (String)object);
        try {
            activity = NavUtils.getParentActivityName((Context)activity, componentName) == null ? Intent.makeMainActivity((ComponentName)componentName) : new Intent().setComponent(componentName);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)("getParentActivityIntent: bad parentActivityName '" + (String)object + "' in manifest"));
            return null;
        }
        return activity;
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string2 = NavUtils.getParentActivityName(context, componentName);
        if (string2 == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, componentName = new ComponentName(componentName.getPackageName(), string2)) == null) {
            return Intent.makeMainActivity((ComponentName)componentName);
        }
        return new Intent().setComponent(componentName);
    }

    public static Intent getParentActivityIntent(Context context, Class<?> object) throws PackageManager.NameNotFoundException {
        if ((object = NavUtils.getParentActivityName(context, new ComponentName(context, object))) == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, (ComponentName)(object = new ComponentName(context, (String)object))) == null) {
            return Intent.makeMainActivity((ComponentName)object);
        }
        return new Intent().setComponent((ComponentName)object);
    }

    public static String getParentActivityName(Activity object) {
        try {
            object = NavUtils.getParentActivityName((Context)object, object.getComponentName());
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new IllegalArgumentException(nameNotFoundException);
        }
    }

    public static String getParentActivityName(Context context, ComponentName object) throws PackageManager.NameNotFoundException {
        String string2;
        object = context.getPackageManager().getActivityInfo(object, 128);
        if (Build.VERSION.SDK_INT >= 16 && (string2 = object.parentActivityName) != null) {
            return string2;
        }
        if (object.metaData == null) {
            return null;
        }
        string2 = object.metaData.getString(PARENT_ACTIVITY);
        if (string2 == null) {
            return null;
        }
        object = string2;
        if (string2.charAt(0) == '.') {
            object = context.getPackageName() + string2;
        }
        return object;
    }

    public static void navigateUpFromSameTask(Activity activity) {
        Intent intent = NavUtils.getParentActivityIntent(activity);
        if (intent == null) {
            throw new IllegalArgumentException("Activity " + activity.getClass().getSimpleName() + " does not have a parent activity name specified." + " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " + " element in your manifest?)");
        }
        NavUtils.navigateUpTo(activity, intent);
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.navigateUpTo(intent);
            return;
        }
        intent.addFlags(67108864);
        activity.startActivity(intent);
        activity.finish();
    }

    public static boolean shouldUpRecreateTask(Activity object, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return object.shouldUpRecreateTask(intent);
        }
        return (object = object.getIntent().getAction()) != null && !((String)object).equals("android.intent.action.MAIN");
    }
}

