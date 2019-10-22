/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.crashlytics.android.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.CheckForUpdatesRequest;
import com.crashlytics.android.beta.CheckForUpdatesResponse;
import com.crashlytics.android.beta.CheckForUpdatesResponseTransform;
import com.crashlytics.android.beta.UpdatesController;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractCheckForUpdatesController
implements UpdatesController {
    private Beta beta;
    private BetaSettingsData betaSettings;
    private BuildProperties buildProps;
    private Context context;
    private CurrentTimeProvider currentTimeProvider;
    private final AtomicBoolean externallyReady;
    private HttpRequestFactory httpRequestFactory;
    private IdManager idManager;
    private final AtomicBoolean initialized = new AtomicBoolean();
    private long lastCheckTimeMillis = 0L;
    private PreferenceStore preferenceStore;

    public AbstractCheckForUpdatesController() {
        this(false);
    }

    public AbstractCheckForUpdatesController(boolean bl) {
        this.externallyReady = new AtomicBoolean(bl);
    }

    private void performUpdateCheck() {
        Fabric.getLogger().d("Beta", "Performing update check");
        String string2 = new ApiKey().getValue(this.context);
        String string3 = this.idManager.getDeviceIdentifiers().get((Object)IdManager.DeviceIdentifierType.FONT_TOKEN);
        new CheckForUpdatesRequest(this.beta, this.beta.getOverridenSpiEndpoint(), this.betaSettings.updateUrl, this.httpRequestFactory, new CheckForUpdatesResponseTransform()).invoke(string2, string3, this.buildProps);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"CommitPrefEdits"})
    protected void checkForUpdates() {
        PreferenceStore preferenceStore = this.preferenceStore;
        synchronized (preferenceStore) {
            if (this.preferenceStore.get().contains("last_update_check")) {
                this.preferenceStore.save(this.preferenceStore.edit().remove("last_update_check"));
            }
        }
        long l = this.currentTimeProvider.getCurrentTimeMillis();
        long l2 = (long)this.betaSettings.updateSuspendDurationSeconds * 1000L;
        Fabric.getLogger().d("Beta", "Check for updates delay: " + l2);
        Fabric.getLogger().d("Beta", "Check for updates last check time: " + this.getLastCheckTimeMillis());
        l2 = this.getLastCheckTimeMillis() + l2;
        Fabric.getLogger().d("Beta", "Check for updates current time: " + l + ", next check time: " + l2);
        if (l < l2) {
            Fabric.getLogger().d("Beta", "Check for updates next check time was not passed");
            return;
        }
        try {
            this.performUpdateCheck();
            return;
        }
        finally {
            this.setLastCheckTimeMillis(l);
        }
    }

    long getLastCheckTimeMillis() {
        return this.lastCheckTimeMillis;
    }

    @Override
    public void initialize(Context context, Beta beta, IdManager idManager, BetaSettingsData betaSettingsData, BuildProperties buildProperties, PreferenceStore preferenceStore, CurrentTimeProvider currentTimeProvider, HttpRequestFactory httpRequestFactory) {
        this.context = context;
        this.beta = beta;
        this.idManager = idManager;
        this.betaSettings = betaSettingsData;
        this.buildProps = buildProperties;
        this.preferenceStore = preferenceStore;
        this.currentTimeProvider = currentTimeProvider;
        this.httpRequestFactory = httpRequestFactory;
        if (this.signalInitialized()) {
            this.checkForUpdates();
        }
    }

    void setLastCheckTimeMillis(long l) {
        this.lastCheckTimeMillis = l;
    }

    protected boolean signalExternallyReady() {
        this.externallyReady.set(true);
        return this.initialized.get();
    }

    boolean signalInitialized() {
        this.initialized.set(true);
        return this.externallyReady.get();
    }
}

