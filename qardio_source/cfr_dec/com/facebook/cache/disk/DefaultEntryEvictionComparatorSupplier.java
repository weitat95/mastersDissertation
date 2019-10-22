/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.disk;

import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.EntryEvictionComparator;
import com.facebook.cache.disk.EntryEvictionComparatorSupplier;

public class DefaultEntryEvictionComparatorSupplier
implements EntryEvictionComparatorSupplier {
    @Override
    public EntryEvictionComparator get() {
        return new EntryEvictionComparator(){

            @Override
            public int compare(DiskStorage.Entry entry, DiskStorage.Entry entry2) {
                long l;
                long l2 = entry.getTimestamp();
                if (l2 < (l = entry2.getTimestamp())) {
                    return -1;
                }
                if (l == l2) {
                    return 0;
                }
                return 1;
            }
        };
    }

}

