/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import java.io.InputStream;

public interface PinningInfoProvider {
    public String getKeyStorePassword();

    public InputStream getKeyStoreStream();

    public long getPinCreationTimeInMillis();

    public String[] getPins();
}

