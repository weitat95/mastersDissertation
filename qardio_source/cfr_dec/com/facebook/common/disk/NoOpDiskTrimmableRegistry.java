/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.disk;

import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;

public class NoOpDiskTrimmableRegistry
implements DiskTrimmableRegistry {
    private static NoOpDiskTrimmableRegistry sInstance = null;

    private NoOpDiskTrimmableRegistry() {
    }

    public static NoOpDiskTrimmableRegistry getInstance() {
        synchronized (NoOpDiskTrimmableRegistry.class) {
            if (sInstance == null) {
                sInstance = new NoOpDiskTrimmableRegistry();
            }
            NoOpDiskTrimmableRegistry noOpDiskTrimmableRegistry = sInstance;
            return noOpDiskTrimmableRegistry;
        }
    }

    @Override
    public void registerDiskTrimmable(DiskTrimmable diskTrimmable) {
    }
}

