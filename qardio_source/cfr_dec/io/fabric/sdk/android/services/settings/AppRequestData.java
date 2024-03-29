/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.settings.IconRequest;
import java.util.Collection;

public class AppRequestData {
    public final String apiKey;
    public final String appId;
    public final String buildVersion;
    public final String builtSdkVersion;
    public final String displayVersion;
    public final IconRequest icon;
    public final String instanceIdentifier;
    public final String minSdkVersion;
    public final String name;
    public final Collection<KitInfo> sdkKits;
    public final int source;

    public AppRequestData(String string2, String string3, String string4, String string5, String string6, String string7, int n, String string8, String string9, IconRequest iconRequest, Collection<KitInfo> collection) {
        this.apiKey = string2;
        this.appId = string3;
        this.displayVersion = string4;
        this.buildVersion = string5;
        this.instanceIdentifier = string6;
        this.name = string7;
        this.source = n;
        this.minSdkVersion = string8;
        this.builtSdkVersion = string9;
        this.icon = iconRequest;
        this.sdkKits = collection;
    }
}

