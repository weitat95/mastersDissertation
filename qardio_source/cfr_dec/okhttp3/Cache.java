/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.InternalCache;

public final class Cache
implements Closeable,
Flushable {
    final DiskLruCache cache;
    final InternalCache internalCache;

    @Override
    public void close() throws IOException {
        this.cache.close();
    }

    @Override
    public void flush() throws IOException {
        this.cache.flush();
    }
}

