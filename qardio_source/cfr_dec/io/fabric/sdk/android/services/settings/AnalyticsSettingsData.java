/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class AnalyticsSettingsData {
    public final String analyticsURL;
    public final int flushIntervalSeconds;
    public final boolean flushOnBackground;
    public final int maxByteSizePerFile;
    public final int maxFileCountPerSend;
    public final int maxPendingSendFileCount;
    public final int samplingRate;
    public final boolean trackCustomEvents;
    public final boolean trackPredefinedEvents;

    public AnalyticsSettingsData(String string2, int n, int n2, int n3, int n4, boolean bl, boolean bl2, int n5, boolean bl3) {
        this.analyticsURL = string2;
        this.flushIntervalSeconds = n;
        this.maxByteSizePerFile = n2;
        this.maxFileCountPerSend = n3;
        this.maxPendingSendFileCount = n4;
        this.trackCustomEvents = bl;
        this.trackPredefinedEvents = bl2;
        this.samplingRate = n5;
        this.flushOnBackground = bl3;
    }
}

