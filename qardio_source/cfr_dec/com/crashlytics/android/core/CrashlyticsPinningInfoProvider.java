/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.PinningInfoProvider;
import java.io.InputStream;

class CrashlyticsPinningInfoProvider
implements io.fabric.sdk.android.services.network.PinningInfoProvider {
    private final PinningInfoProvider pinningInfo;

    public CrashlyticsPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        this.pinningInfo = pinningInfoProvider;
    }

    @Override
    public String getKeyStorePassword() {
        return this.pinningInfo.getKeyStorePassword();
    }

    @Override
    public InputStream getKeyStoreStream() {
        return this.pinningInfo.getKeyStoreStream();
    }

    @Override
    public long getPinCreationTimeInMillis() {
        return -1L;
    }

    @Override
    public String[] getPins() {
        return this.pinningInfo.getPins();
    }
}

