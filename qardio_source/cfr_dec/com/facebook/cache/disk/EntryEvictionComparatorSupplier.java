/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.disk;

import com.facebook.cache.disk.EntryEvictionComparator;

public interface EntryEvictionComparatorSupplier {
    public EntryEvictionComparator get();
}

