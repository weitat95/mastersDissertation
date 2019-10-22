/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.cache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class CacheInterceptor
implements Interceptor {
    final InternalCache cache;

    public CacheInterceptor(InternalCache internalCache) {
        this.cache = internalCache;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Response cacheWritingResponse(CacheRequest object, Response response) throws IOException {
        Sink sink;
        if (object == null || (sink = object.body()) == null) {
            return response;
        }
        object = new Source(response.body().source(), (CacheRequest)object, Okio.buffer(sink)){
            boolean cacheRequestClosed;
            final /* synthetic */ BufferedSink val$cacheBody;
            final /* synthetic */ CacheRequest val$cacheRequest;
            final /* synthetic */ BufferedSource val$source;
            {
                this.val$source = bufferedSource;
                this.val$cacheRequest = cacheRequest;
                this.val$cacheBody = bufferedSink;
            }

            @Override
            public void close() throws IOException {
                if (!this.cacheRequestClosed && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.cacheRequestClosed = true;
                    this.val$cacheRequest.abort();
                }
                this.val$source.close();
            }

            @Override
            public long read(Buffer buffer, long l) throws IOException {
                block3: {
                    block4: {
                        try {
                            l = this.val$source.read(buffer, l);
                            if (l != -1L) break block3;
                            if (this.cacheRequestClosed) break block4;
                            this.cacheRequestClosed = true;
                        }
                        catch (IOException iOException) {
                            if (!this.cacheRequestClosed) {
                                this.cacheRequestClosed = true;
                                this.val$cacheRequest.abort();
                            }
                            throw iOException;
                        }
                        this.val$cacheBody.close();
                    }
                    return -1L;
                }
                buffer.copyTo(this.val$cacheBody.buffer(), buffer.size() - l, l);
                this.val$cacheBody.emitCompleteSegments();
                return l;
            }

            @Override
            public Timeout timeout() {
                return this.val$source.timeout();
            }
        };
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer((Source)object))).build();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Headers combine(Headers object, Headers headers) {
        int n;
        Headers.Builder builder = new Headers.Builder();
        int n2 = ((Headers)object).size();
        for (n = 0; n < n2; ++n) {
            String string2 = ((Headers)object).name(n);
            String string3 = ((Headers)object).value(n);
            if ("Warning".equalsIgnoreCase(string2) && string3.startsWith("1") || CacheInterceptor.isEndToEnd(string2) && headers.get(string2) != null) continue;
            Internal.instance.addLenient(builder, string2, string3);
        }
        n = 0;
        n2 = headers.size();
        while (n < n2) {
            object = headers.name(n);
            if (!"Content-Length".equalsIgnoreCase((String)object) && CacheInterceptor.isEndToEnd((String)object)) {
                Internal.instance.addLenient(builder, (String)object, headers.value(n));
            }
            ++n;
        }
        return builder.build();
    }

    static boolean isEndToEnd(String string2) {
        return !"Connection".equalsIgnoreCase(string2) && !"Keep-Alive".equalsIgnoreCase(string2) && !"Proxy-Authenticate".equalsIgnoreCase(string2) && !"Proxy-Authorization".equalsIgnoreCase(string2) && !"TE".equalsIgnoreCase(string2) && !"Trailers".equalsIgnoreCase(string2) && !"Transfer-Encoding".equalsIgnoreCase(string2) && !"Upgrade".equalsIgnoreCase(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CacheRequest maybeCache(Response response, Request request, InternalCache internalCache) throws IOException {
        block7: {
            block6: {
                if (internalCache == null) break block6;
                if (CacheStrategy.isCacheable(response, request)) {
                    return internalCache.put(response);
                }
                if (HttpMethod.invalidatesCache(request.method())) break block7;
            }
            return null;
        }
        try {
            internalCache.remove(request);
            return null;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private static Response stripBody(Response response) {
        Response response2 = response;
        if (response != null) {
            response2 = response;
            if (response.body() != null) {
                response2 = response.newBuilder().body(null).build();
            }
        }
        return response2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        Object object2;
        Response response;
        Response response2;
        block9: {
            response2 = this.cache != null ? this.cache.get(object.request()) : null;
            object2 = new CacheStrategy.Factory(System.currentTimeMillis(), object.request(), response2).get();
            Request request = ((CacheStrategy)object2).networkRequest;
            response = ((CacheStrategy)object2).cacheResponse;
            if (this.cache != null) {
                this.cache.trackResponse((CacheStrategy)object2);
            }
            if (response2 != null && response == null) {
                Util.closeQuietly(response2.body());
            }
            if (request == null && response == null) {
                return new Response.Builder().request(object.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
            }
            if (request == null) {
                return response.newBuilder().cacheResponse(CacheInterceptor.stripBody(response)).build();
            }
            try {
                object2 = object.proceed(request);
                if (response == null) break block9;
                if (((Response)object2).code() == 304) {
                    object = response.newBuilder().headers(CacheInterceptor.combine(response.headers(), ((Response)object2).headers())).sentRequestAtMillis(((Response)object2).sentRequestAtMillis()).receivedResponseAtMillis(((Response)object2).receivedResponseAtMillis()).cacheResponse(CacheInterceptor.stripBody(response)).networkResponse(CacheInterceptor.stripBody((Response)object2)).build();
                    ((Response)object2).body().close();
                    this.cache.trackConditionalCacheHit();
                    this.cache.update(response, (Response)object);
                    return object;
                }
            }
            finally {
                if (object2 == null && response2 != null) {
                    Util.closeQuietly(response2.body());
                }
            }
            Util.closeQuietly(response.body());
        }
        response2 = ((Response)object2).newBuilder().cacheResponse(CacheInterceptor.stripBody(response)).networkResponse(CacheInterceptor.stripBody((Response)object2)).build();
        object = response2;
        if (!HttpHeaders.hasBody(response2)) return object;
        return this.cacheWritingResponse(this.maybeCache(response2, ((Response)object2).request(), this.cache), response2);
    }

}

