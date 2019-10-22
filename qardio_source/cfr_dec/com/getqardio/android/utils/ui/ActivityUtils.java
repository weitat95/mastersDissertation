/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.Window
 */
package com.getqardio.android.utils.ui;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class ActivityUtils {
    public static void changeStatusBarColor(Activity activity, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity = activity.getWindow();
            activity.addFlags(Integer.MIN_VALUE);
            activity.setStatusBarColor(n);
        }
    }

    public static ActionBar getActionBar(Activity activity) {
        return ((AppCompatActivity)activity).getSupportActionBar();
    }
}

