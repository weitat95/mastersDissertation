/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Handler
 */
package com.getqardio.android.mvp.activity_tracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.JobIntentService;

public class ActivityTrackerServiceOreo
extends JobIntentService {
    private final Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleWork(Intent intent) {
    }
}

