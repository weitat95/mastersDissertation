/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class SettingsRequest {
    public final String advertisingId;
    public final String androidId;
    public final String apiKey;
    public final String buildVersion;
    public final String deviceModel;
    public final String displayVersion;
    public final String iconHash;
    public final String installationId;
    public final String instanceId;
    public final String osBuildVersion;
    public final String osDisplayVersion;
    public final int source;

    public SettingsRequest(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, int n, String string12) {
        this.apiKey = string2;
        this.deviceModel = string3;
        this.osBuildVersion = string4;
        this.osDisplayVersion = string5;
        this.advertisingId = string6;
        this.installationId = string7;
        this.androidId = string8;
        this.instanceId = string9;
        this.displayVersion = string10;
        this.buildVersion = string11;
        this.source = n;
        this.iconHash = string12;
    }
}

