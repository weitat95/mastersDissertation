/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.common.disk.DiskTrimmable;
import java.io.IOException;

public interface FileCache
extends DiskTrimmable {
    public BinaryResource getResource(CacheKey var1);

    public boolean hasKeySync(CacheKey var1);

    public BinaryResource insert(CacheKey var1, WriterCallback var2) throws IOException;
}

