/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.cache;

public class MemoryCacheParams {
    public final int maxCacheEntries;
    public final int maxCacheEntrySize;
    public final int maxCacheSize;
    public final int maxEvictionQueueEntries;
    public final int maxEvictionQueueSize;

    public MemoryCacheParams(int n, int n2, int n3, int n4, int n5) {
        this.maxCacheSize = n;
        this.maxCacheEntries = n2;
        this.maxEvictionQueueSize = n3;
        this.maxEvictionQueueEntries = n4;
        this.maxCacheEntrySize = n5;
    }
}

