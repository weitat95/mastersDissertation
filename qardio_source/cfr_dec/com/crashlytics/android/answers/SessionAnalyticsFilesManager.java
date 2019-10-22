/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.answers;

import android.content.Context;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventTransform;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventTransform;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;
import java.util.UUID;

class SessionAnalyticsFilesManager
extends EventsFilesManager<SessionEvent> {
    private AnalyticsSettingsData analyticsSettingsData;

    SessionAnalyticsFilesManager(Context context, SessionEventTransform sessionEventTransform, CurrentTimeProvider currentTimeProvider, EventsStorage eventsStorage) throws IOException {
        super(context, sessionEventTransform, currentTimeProvider, eventsStorage, 100);
    }

    @Override
    protected String generateUniqueRollOverFileName() {
        UUID uUID = UUID.randomUUID();
        return "sa" + "_" + uUID.toString() + "_" + this.currentTimeProvider.getCurrentTimeMillis() + ".tap";
    }

    @Override
    protected int getMaxByteSizePerFile() {
        if (this.analyticsSettingsData == null) {
            return super.getMaxByteSizePerFile();
        }
        return this.analyticsSettingsData.maxByteSizePerFile;
    }

    @Override
    protected int getMaxFilesToKeep() {
        if (this.analyticsSettingsData == null) {
            return super.getMaxFilesToKeep();
        }
        return this.analyticsSettingsData.maxPendingSendFileCount;
    }

    void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData) {
        this.analyticsSettingsData = analyticsSettingsData;
    }
}

