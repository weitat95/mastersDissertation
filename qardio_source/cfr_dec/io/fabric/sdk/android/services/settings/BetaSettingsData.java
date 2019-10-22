/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class BetaSettingsData {
    public final int updateSuspendDurationSeconds;
    public final String updateUrl;

    public BetaSettingsData(String string2, int n) {
        this.updateUrl = string2;
        this.updateSuspendDurationSeconds = n;
    }
}

