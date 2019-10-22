/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Looper
 */
package com.crashlytics.android.answers;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.crashlytics.android.answers.AnswersEventsHandler;
import com.crashlytics.android.answers.AnswersFilesManagerProvider;
import com.crashlytics.android.answers.AnswersLifecycleCallbacks;
import com.crashlytics.android.answers.AnswersPreferenceManager;
import com.crashlytics.android.answers.BackgroundManager;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionMetadataCollector;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class SessionAnalyticsManager
implements BackgroundManager.Listener {
    final BackgroundManager backgroundManager;
    final AnswersEventsHandler eventsHandler;
    private final long installedAt;
    final ActivityLifecycleManager lifecycleManager;
    final AnswersPreferenceManager preferenceManager;

    SessionAnalyticsManager(AnswersEventsHandler answersEventsHandler, ActivityLifecycleManager activityLifecycleManager, BackgroundManager backgroundManager, AnswersPreferenceManager answersPreferenceManager, long l) {
        this.eventsHandler = answersEventsHandler;
        this.lifecycleManager = activityLifecycleManager;
        this.backgroundManager = backgroundManager;
        this.preferenceManager = answersPreferenceManager;
        this.installedAt = l;
    }

    public static SessionAnalyticsManager build(Kit kit, Context context, IdManager object, String object2, String object3, long l) {
        object = new SessionMetadataCollector(context, (IdManager)object, (String)object2, (String)object3);
        object2 = new AnswersFilesManagerProvider(context, new FileStoreImpl(kit));
        object3 = new DefaultHttpRequestFactory(Fabric.getLogger());
        ActivityLifecycleManager activityLifecycleManager = new ActivityLifecycleManager(context);
        ScheduledExecutorService scheduledExecutorService = ExecutorUtils.buildSingleThreadScheduledExecutorService("Answers Events Handler");
        BackgroundManager backgroundManager = new BackgroundManager(scheduledExecutorService);
        return new SessionAnalyticsManager(new AnswersEventsHandler(kit, context, (AnswersFilesManagerProvider)object2, (SessionMetadataCollector)object, (HttpRequestFactory)object3, scheduledExecutorService), activityLifecycleManager, backgroundManager, AnswersPreferenceManager.build(context), l);
    }

    public void disable() {
        this.lifecycleManager.resetCallbacks();
        this.eventsHandler.disable();
    }

    public void enable() {
        this.eventsHandler.enable();
        this.lifecycleManager.registerCallbacks(new AnswersLifecycleCallbacks(this, this.backgroundManager));
        this.backgroundManager.registerListener(this);
        if (this.isFirstLaunch()) {
            this.onInstall(this.installedAt);
            this.preferenceManager.setAnalyticsLaunched();
        }
    }

    boolean isFirstLaunch() {
        return !this.preferenceManager.hasAnalyticsLaunched();
    }

    @Override
    public void onBackground() {
        Fabric.getLogger().d("Answers", "Flush events when app is backgrounded");
        this.eventsHandler.flushEvents();
    }

    public void onCrash(String string2, String string3) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("onCrash called from main thread!!!");
        }
        Fabric.getLogger().d("Answers", "Logged crash");
        this.eventsHandler.processEventSync(SessionEvent.crashEventBuilder(string2, string3));
    }

    public void onCustom(CustomEvent customEvent) {
        Fabric.getLogger().d("Answers", "Logged custom event: " + customEvent);
        this.eventsHandler.processEventAsync(SessionEvent.customEventBuilder(customEvent));
    }

    public void onError(String string2) {
    }

    public void onInstall(long l) {
        Fabric.getLogger().d("Answers", "Logged install");
        this.eventsHandler.processEventAsyncAndFlush(SessionEvent.installEventBuilder(l));
    }

    public void onLifecycle(Activity activity, SessionEvent.Type type) {
        Fabric.getLogger().d("Answers", "Logged lifecycle event: " + type.name());
        this.eventsHandler.processEventAsync(SessionEvent.lifecycleEventBuilder(type, activity));
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String string2) {
        this.backgroundManager.setFlushOnBackground(analyticsSettingsData.flushOnBackground);
        this.eventsHandler.setAnalyticsSettingsData(analyticsSettingsData, string2);
    }
}

