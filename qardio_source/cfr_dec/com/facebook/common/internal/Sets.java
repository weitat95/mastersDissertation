/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class Sets {
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Sets.newSetFromMap(new IdentityHashMap());
    }

    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }
}

