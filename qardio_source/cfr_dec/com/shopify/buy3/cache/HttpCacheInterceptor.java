/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.HttpCache;
import com.shopify.buy3.cache.Utils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

final class HttpCacheInterceptor
implements Interceptor {
    private final HttpCache cache;

    HttpCacheInterceptor(HttpCache httpCache) {
        this.cache = httpCache;
    }

    private Response cacheFirst(Request object, Interceptor.Chain object2) throws IOException {
        Response response = this.cachedResponse((Request)object);
        if (response == null) {
            this.logCacheMiss((Request)object);
            response = Utils.withServedDateHeader(object2.proceed((Request)object));
            object2 = response;
            if (response.isSuccessful()) {
                object = ((Request)object).header("X-BUY3-SDK-CACHE-KEY");
                object2 = this.cache.proxyResponse(response, (String)object);
            }
            return object2;
        }
        this.logCacheHit((Request)object);
        return response.newBuilder().cacheResponse(Utils.strip(response)).request((Request)object).build();
    }

    private Response cacheOnlyResponse(Request request) throws IOException {
        Response response = this.cachedResponse(request);
        if (response == null) {
            this.logCacheMiss(request);
            return Utils.unsatisfiableCacheRequest(request);
        }
        this.logCacheHit(request);
        return response.newBuilder().cacheResponse(Utils.strip(response)).build();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Response cachedResponse(Request request) {
        Object object = request.header("X-BUY3-SDK-CACHE-KEY");
        if (object == null) return null;
        if (((String)object).isEmpty()) {
            return null;
        }
        Response response = this.cache.read((String)object);
        if (response == null) {
            return null;
        }
        object = response;
        if (!Utils.isStale(request, response)) return object;
        Utils.closeQuietly(response);
        return null;
    }

    private void logCacheHit(Request object) {
        if ((object = ((Request)object).header("X-BUY3-SDK-CACHE-KEY")) == null || ((String)object).isEmpty()) {
            return;
        }
        Timber.d("cache HIT for key: %s", object);
    }

    private void logCacheMiss(Request object) {
        if ((object = ((Request)object).header("X-BUY3-SDK-CACHE-KEY")) == null || ((String)object).isEmpty()) {
            return;
        }
        Timber.d("cache MISS for key: %s", object);
    }

    private Response networkFirst(Request object, Interceptor.Chain object2) throws IOException {
        if (((Response)(object2 = Utils.withServedDateHeader(object2.proceed((Request)object)))).isSuccessful()) {
            object = ((Request)object).header("X-BUY3-SDK-CACHE-KEY");
            return this.cache.proxyResponse((Response)object2, (String)object);
        }
        Response response = this.cachedResponse((Request)object);
        if (response == null) {
            this.logCacheMiss((Request)object);
            return object2;
        }
        this.logCacheHit((Request)object);
        return response.newBuilder().cacheResponse(Utils.strip(response)).networkResponse(Utils.strip((Response)object2)).request((Request)object).build();
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (Utils.shouldSkipCache(request)) {
            return chain.proceed(request);
        }
        if (Utils.shouldSkipNetwork(request)) {
            return this.cacheOnlyResponse(request);
        }
        if (Utils.isNetworkFirst(request)) {
            return this.networkFirst(request, chain);
        }
        return this.cacheFirst(request, chain);
    }
}

