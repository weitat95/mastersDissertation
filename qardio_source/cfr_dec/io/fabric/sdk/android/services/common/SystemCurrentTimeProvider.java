/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;

public class SystemCurrentTimeProvider
implements CurrentTimeProvider {
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}

