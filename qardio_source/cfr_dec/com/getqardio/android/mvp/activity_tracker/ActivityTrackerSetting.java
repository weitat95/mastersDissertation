/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.mvp.activity_tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ActivityTrackerSetting {
    private final Context context;
    private final SharedPreferences trackerPrefs;

    private ActivityTrackerSetting(Context context) {
        this.context = context.getApplicationContext();
        this.trackerPrefs = PreferenceManager.getDefaultSharedPreferences((Context)context);
    }

    public static ActivityTrackerSetting newInstance(Context context) {
        return new ActivityTrackerSetting(context.getApplicationContext());
    }
}

