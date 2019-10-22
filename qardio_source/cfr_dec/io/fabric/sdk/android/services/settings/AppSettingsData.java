/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.AppIconSettingsData;

public class AppSettingsData {
    public final AppIconSettingsData icon;
    public final String identifier;
    public final String reportsUrl;
    public final String status;
    public final boolean updateRequired;
    public final String url;

    public AppSettingsData(String string2, String string3, String string4, String string5, boolean bl, AppIconSettingsData appIconSettingsData) {
        this.identifier = string2;
        this.status = string3;
        this.url = string4;
        this.reportsUrl = string5;
        this.updateRequired = bl;
        this.icon = appIconSettingsData;
    }
}

