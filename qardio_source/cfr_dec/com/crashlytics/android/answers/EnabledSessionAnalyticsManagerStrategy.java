/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.answers;

import android.content.Context;
import com.crashlytics.android.answers.AnswersRetryFilesSender;
import com.crashlytics.android.answers.EventFilter;
import com.crashlytics.android.answers.KeepAllEventFilter;
import com.crashlytics.android.answers.SamplingEventFilter;
import com.crashlytics.android.answers.SessionAnalyticsFilesManager;
import com.crashlytics.android.answers.SessionAnalyticsFilesSender;
import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.FileRollOverManager;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.events.TimeBasedFileRollOverRunnable;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class EnabledSessionAnalyticsManagerStrategy
implements SessionAnalyticsManagerStrategy {
    ApiKey apiKey;
    private final Context context;
    boolean customEventsEnabled = true;
    EventFilter eventFilter;
    private final ScheduledExecutorService executorService;
    private final SessionAnalyticsFilesManager filesManager;
    FilesSender filesSender;
    private final HttpRequestFactory httpRequestFactory;
    private final Kit kit;
    final SessionEventMetadata metadata;
    boolean predefinedEventsEnabled = true;
    private final AtomicReference<ScheduledFuture<?>> rolloverFutureRef = new AtomicReference();
    volatile int rolloverIntervalSeconds = -1;

    public EnabledSessionAnalyticsManagerStrategy(Kit kit, Context context, ScheduledExecutorService scheduledExecutorService, SessionAnalyticsFilesManager sessionAnalyticsFilesManager, HttpRequestFactory httpRequestFactory, SessionEventMetadata sessionEventMetadata) {
        this.apiKey = new ApiKey();
        this.eventFilter = new KeepAllEventFilter();
        this.kit = kit;
        this.context = context;
        this.executorService = scheduledExecutorService;
        this.filesManager = sessionAnalyticsFilesManager;
        this.httpRequestFactory = httpRequestFactory;
        this.metadata = sessionEventMetadata;
    }

    @Override
    public void cancelTimeBasedFileRollOver() {
        if (this.rolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            this.rolloverFutureRef.get().cancel(false);
            this.rolloverFutureRef.set(null);
        }
    }

    @Override
    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processEvent(SessionEvent.Builder object) {
        object = ((SessionEvent.Builder)object).build(this.metadata);
        if (!this.customEventsEnabled && SessionEvent.Type.CUSTOM.equals((Object)((SessionEvent)object).type)) {
            Fabric.getLogger().d("Answers", "Custom events tracking disabled - skipping event: " + object);
            return;
        }
        if (!this.predefinedEventsEnabled && SessionEvent.Type.PREDEFINED.equals((Object)((SessionEvent)object).type)) {
            Fabric.getLogger().d("Answers", "Predefined events tracking disabled - skipping event: " + object);
            return;
        }
        if (this.eventFilter.skipEvent((SessionEvent)object)) {
            Fabric.getLogger().d("Answers", "Skipping filtered event: " + object);
            return;
        }
        try {
            this.filesManager.writeEvent(object);
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("Answers", "Failed to write event: " + object, iOException);
        }
        this.scheduleTimeBasedRollOverIfNeeded();
    }

    @Override
    public boolean rollFileOver() {
        try {
            boolean bl = this.filesManager.rollFileOver();
            return bl;
        }
        catch (IOException iOException) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", iOException);
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void scheduleTimeBasedFileRollOver(long l, long l2) {
        if (this.rolloverFutureRef.get() != null) return;
        boolean bl = true;
        if (!bl) return;
        TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
        CommonUtils.logControlled(this.context, "Scheduling time based file roll over every " + l2 + " seconds");
        try {
            this.rolloverFutureRef.set(this.executorService.scheduleAtFixedRate(timeBasedFileRollOverRunnable, l, l2, TimeUnit.SECONDS));
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", rejectedExecutionException);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void scheduleTimeBasedRollOverIfNeeded() {
        if (this.rolloverIntervalSeconds == -1) return;
        boolean bl = true;
        if (!bl) return;
        this.scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void sendEvents() {
        int n;
        int n2;
        if (this.filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        int n3 = 0;
        List<File> list = this.filesManager.getBatchOfFilesToSend();
        do {
            n2 = n3;
            n = n3;
            if (list.size() > 0) {
                n = n3;
                CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", list.size()));
                n = n3;
                boolean bl = this.filesSender.send(list);
                n2 = n3;
                if (bl) {
                    n = n3;
                    n = n2 = n3 + list.size();
                    this.filesManager.deleteSentFiles(list);
                }
                if (bl) {
                    n = n2;
                    list = this.filesManager.getBatchOfFilesToSend();
                    n3 = n2;
                    continue;
                }
            }
            break;
        } while (true);
        catch (Exception exception) {
            CommonUtils.logControlledError(this.context, "Failed to send batch of analytics files to server: " + exception.getMessage(), exception);
            n2 = n;
        }
        if (n2 != 0) return;
        this.filesManager.deleteOldestInRollOverIfOverMax();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String string2) {
        this.filesSender = AnswersRetryFilesSender.build(new SessionAnalyticsFilesSender(this.kit, string2, analyticsSettingsData.analyticsURL, this.httpRequestFactory, this.apiKey.getValue(this.context)));
        this.filesManager.setAnalyticsSettingsData(analyticsSettingsData);
        this.customEventsEnabled = analyticsSettingsData.trackCustomEvents;
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder().append("Custom event tracking ");
        string2 = this.customEventsEnabled ? "enabled" : "disabled";
        logger.d("Answers", stringBuilder.append(string2).toString());
        this.predefinedEventsEnabled = analyticsSettingsData.trackPredefinedEvents;
        logger = Fabric.getLogger();
        stringBuilder = new StringBuilder().append("Predefined event tracking ");
        string2 = this.predefinedEventsEnabled ? "enabled" : "disabled";
        logger.d("Answers", stringBuilder.append(string2).toString());
        if (analyticsSettingsData.samplingRate > 1) {
            Fabric.getLogger().d("Answers", "Event sampling enabled");
            this.eventFilter = new SamplingEventFilter(analyticsSettingsData.samplingRate);
        }
        this.rolloverIntervalSeconds = analyticsSettingsData.flushIntervalSeconds;
        this.scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
    }
}

