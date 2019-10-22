/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.cache;

import java.io.IOException;
import okio.Sink;

public interface CacheRequest {
    public void abort();

    public Sink body() throws IOException;
}

