/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android;

public class KitInfo {
    private final String buildType;
    private final String identifier;
    private final String version;

    public KitInfo(String string2, String string3, String string4) {
        this.identifier = string2;
        this.version = string3;
        this.buildType = string4;
    }

    public String getBuildType() {
        return this.buildType;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getVersion() {
        return this.version;
    }
}

