/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.Intent
 *  android.os.Handler
 */
package com.getqardio.android.mvp.activity_tracker.common.model.remote;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

public class ActivityRecognizedService
extends IntentService {
    private final Handler handler = new Handler();

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String string2) {
        super(string2);
    }

    public void onCreate() {
        super.onCreate();
    }

    protected void onHandleIntent(Intent intent) {
    }
}

