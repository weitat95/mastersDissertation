/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 */
package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Activity;
import com.crashlytics.android.beta.AbstractCheckForUpdatesController;
import io.fabric.sdk.android.ActivityLifecycleManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@TargetApi(value=14)
class ActivityLifecycleCheckForUpdatesController
extends AbstractCheckForUpdatesController {
    private final ActivityLifecycleManager.Callbacks callbacks = new ActivityLifecycleManager.Callbacks(){

        @Override
        public void onActivityStarted(Activity activity) {
            if (ActivityLifecycleCheckForUpdatesController.this.signalExternallyReady()) {
                ActivityLifecycleCheckForUpdatesController.this.executorService.submit(new Runnable(){

                    @Override
                    public void run() {
                        ActivityLifecycleCheckForUpdatesController.this.checkForUpdates();
                    }
                });
            }
        }

    };
    private final ExecutorService executorService;

    public ActivityLifecycleCheckForUpdatesController(ActivityLifecycleManager activityLifecycleManager, ExecutorService executorService) {
        this.executorService = executorService;
        activityLifecycleManager.registerCallbacks(this.callbacks);
    }

}

