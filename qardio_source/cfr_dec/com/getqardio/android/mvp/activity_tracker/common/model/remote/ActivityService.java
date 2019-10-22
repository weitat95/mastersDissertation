/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.Intent
 */
package com.getqardio.android.mvp.activity_tracker.common.model.remote;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;

public class ActivityService
extends IntentService {
    private static int BUFFER_TIME_MIN;
    private static int DETECTION_THRESHOLD;

    static {
        DETECTION_THRESHOLD = 55;
        BUFFER_TIME_MIN = 2;
    }

    private void handleDetectedActivities(List<DetectedActivity> list) {
    }

    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            this.handleDetectedActivities(ActivityRecognitionResult.extractResult(intent).getProbableActivities());
        }
    }
}

