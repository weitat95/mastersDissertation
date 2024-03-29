/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;

public class SettingsData {
    public final AnalyticsSettingsData analyticsSettingsData;
    public final AppSettingsData appData;
    public final BetaSettingsData betaSettingsData;
    public final int cacheDuration;
    public final long expiresAtMillis;
    public final FeaturesSettingsData featuresData;
    public final PromptSettingsData promptData;
    public final SessionSettingsData sessionData;
    public final int settingsVersion;

    public SettingsData(long l, AppSettingsData appSettingsData, SessionSettingsData sessionSettingsData, PromptSettingsData promptSettingsData, FeaturesSettingsData featuresSettingsData, AnalyticsSettingsData analyticsSettingsData, BetaSettingsData betaSettingsData, int n, int n2) {
        this.expiresAtMillis = l;
        this.appData = appSettingsData;
        this.sessionData = sessionSettingsData;
        this.promptData = promptSettingsData;
        this.featuresData = featuresSettingsData;
        this.settingsVersion = n;
        this.cacheDuration = n2;
        this.analyticsSettingsData = analyticsSettingsData;
        this.betaSettingsData = betaSettingsData;
    }

    public boolean isExpired(long l) {
        return this.expiresAtMillis < l;
    }
}

