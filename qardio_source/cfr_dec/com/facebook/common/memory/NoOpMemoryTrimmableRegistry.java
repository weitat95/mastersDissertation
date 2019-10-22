/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.memory;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;

public class NoOpMemoryTrimmableRegistry
implements MemoryTrimmableRegistry {
    private static NoOpMemoryTrimmableRegistry sInstance = null;

    public static NoOpMemoryTrimmableRegistry getInstance() {
        synchronized (NoOpMemoryTrimmableRegistry.class) {
            if (sInstance == null) {
                sInstance = new NoOpMemoryTrimmableRegistry();
            }
            NoOpMemoryTrimmableRegistry noOpMemoryTrimmableRegistry = sInstance;
            return noOpMemoryTrimmableRegistry;
        }
    }

    @Override
    public void registerMemoryTrimmable(MemoryTrimmable memoryTrimmable) {
    }
}

