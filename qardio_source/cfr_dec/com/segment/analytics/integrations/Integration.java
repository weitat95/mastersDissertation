/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package com.segment.analytics.integrations;

import android.app.Activity;
import android.os.Bundle;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;

public abstract class Integration<T> {
    public void alias(AliasPayload aliasPayload) {
    }

    public void flush() {
    }

    public void group(GroupPayload groupPayload) {
    }

    public void identify(IdentifyPayload identifyPayload) {
    }

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

    public void reset() {
    }

    public void screen(ScreenPayload screenPayload) {
    }

    public void track(TrackPayload trackPayload) {
    }

    public static interface Factory {
        public Integration<?> create(ValueMap var1, Analytics var2);

        public String key();
    }

}

