/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.cache;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;

public interface InternalCache {
    public Response get(Request var1) throws IOException;

    public CacheRequest put(Response var1) throws IOException;

    public void remove(Request var1) throws IOException;

    public void trackConditionalCacheHit();

    public void trackResponse(CacheStrategy var1);

    public void update(Response var1, Response var2);
}

