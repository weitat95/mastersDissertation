/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.memory;

import com.facebook.common.memory.MemoryUiTrimmable;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class MemoryUiTrimmableRegistry {
    private static final Set<MemoryUiTrimmable> sUiTrimmables = Collections.newSetFromMap(new WeakHashMap());

    public static void registerUiTrimmable(MemoryUiTrimmable memoryUiTrimmable) {
        sUiTrimmables.add(memoryUiTrimmable);
    }
}

