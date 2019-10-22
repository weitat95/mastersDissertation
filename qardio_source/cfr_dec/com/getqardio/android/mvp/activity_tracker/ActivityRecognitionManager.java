/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.mvp.activity_tracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerReceiverOreo;

public class ActivityRecognitionManager {
    private PendingIntent activityPI;
    private final Context context;

    private ActivityRecognitionManager(Context context) {
        this.context = context.getApplicationContext();
        this.activityPI = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)new Intent(context, ActivityTrackerReceiverOreo.class), (int)134217728);
    }

    public static ActivityRecognitionManager newInstance(Context context) {
        return new ActivityRecognitionManager(context);
    }
}

