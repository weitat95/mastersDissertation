/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.IdManager;
import java.util.Map;

public interface DeviceIdentifierProvider {
    public Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers();
}

