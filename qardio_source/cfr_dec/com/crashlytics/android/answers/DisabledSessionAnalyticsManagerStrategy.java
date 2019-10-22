/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionEvent;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;

class DisabledSessionAnalyticsManagerStrategy
implements SessionAnalyticsManagerStrategy {
    DisabledSessionAnalyticsManagerStrategy() {
    }

    @Override
    public void cancelTimeBasedFileRollOver() {
    }

    @Override
    public void deleteAllEvents() {
    }

    @Override
    public void processEvent(SessionEvent.Builder builder) {
    }

    @Override
    public boolean rollFileOver() throws IOException {
        return false;
    }

    @Override
    public void sendEvents() {
    }

    @Override
    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String string2) {
    }
}

