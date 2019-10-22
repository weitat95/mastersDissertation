/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class FeaturesSettingsData {
    public final boolean collectAnalytics;
    public final boolean collectLoggedException;
    public final boolean collectReports;
    public final boolean promptEnabled;

    public FeaturesSettingsData(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.promptEnabled = bl;
        this.collectLoggedException = bl2;
        this.collectReports = bl3;
        this.collectAnalytics = bl4;
    }
}

