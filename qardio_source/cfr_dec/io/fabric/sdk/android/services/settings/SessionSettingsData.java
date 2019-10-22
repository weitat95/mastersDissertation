/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class SessionSettingsData {
    public final int identifierMask;
    public final int logBufferSize;
    public final int maxChainedExceptionDepth;
    public final int maxCustomExceptionEvents;
    public final int maxCustomKeyValuePairs;
    public final boolean sendSessionWithoutCrash;

    public SessionSettingsData(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.logBufferSize = n;
        this.maxChainedExceptionDepth = n2;
        this.maxCustomExceptionEvents = n3;
        this.maxCustomKeyValuePairs = n4;
        this.identifierMask = n5;
        this.sendSessionWithoutCrash = bl;
    }
}

