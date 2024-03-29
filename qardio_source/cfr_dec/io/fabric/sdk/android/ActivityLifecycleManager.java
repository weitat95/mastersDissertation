/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

public class ActivityLifecycleManager {
    private final Application application;
    private ActivityLifecycleCallbacksWrapper callbacksWrapper;

    public ActivityLifecycleManager(Context context) {
        this.application = (Application)context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 14) {
            this.callbacksWrapper = new ActivityLifecycleCallbacksWrapper(this.application);
        }
    }

    public boolean registerCallbacks(Callbacks callbacks) {
        return this.callbacksWrapper != null && this.callbacksWrapper.registerLifecycleCallbacks(callbacks);
    }

    public void resetCallbacks() {
        if (this.callbacksWrapper != null) {
            this.callbacksWrapper.clearCallbacks();
        }
    }

    private static class ActivityLifecycleCallbacksWrapper {
        private final Application application;
        private final Set<Application.ActivityLifecycleCallbacks> registeredCallbacks = new HashSet<Application.ActivityLifecycleCallbacks>();

        ActivityLifecycleCallbacksWrapper(Application application) {
            this.application = application;
        }

        @TargetApi(value=14)
        private void clearCallbacks() {
            for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : this.registeredCallbacks) {
                this.application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
            }
        }

        @TargetApi(value=14)
        private boolean registerLifecycleCallbacks(Callbacks object) {
            if (this.application != null) {
                object = new Application.ActivityLifecycleCallbacks((Callbacks)object){
                    final /* synthetic */ Callbacks val$callbacks;
                    {
                        this.val$callbacks = callbacks;
                    }

                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        this.val$callbacks.onActivityCreated(activity, bundle);
                    }

                    public void onActivityDestroyed(Activity activity) {
                        this.val$callbacks.onActivityDestroyed(activity);
                    }

                    public void onActivityPaused(Activity activity) {
                        this.val$callbacks.onActivityPaused(activity);
                    }

                    public void onActivityResumed(Activity activity) {
                        this.val$callbacks.onActivityResumed(activity);
                    }

                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                        this.val$callbacks.onActivitySaveInstanceState(activity, bundle);
                    }

                    public void onActivityStarted(Activity activity) {
                        this.val$callbacks.onActivityStarted(activity);
                    }

                    public void onActivityStopped(Activity activity) {
                        this.val$callbacks.onActivityStopped(activity);
                    }
                };
                this.application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)object);
                this.registeredCallbacks.add((Application.ActivityLifecycleCallbacks)object);
                return true;
            }
            return false;
        }

    }

    public static abstract class Callbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }
    }

}

