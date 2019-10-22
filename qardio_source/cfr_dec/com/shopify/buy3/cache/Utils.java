/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.cache.ResponseCacheRecord;
import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

final class Utils {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void abortQuietly(ResponseCacheRecordEditor responseCacheRecordEditor) {
        if (responseCacheRecordEditor == null) return;
        try {
            responseCacheRecordEditor.abort();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void closeQuietly(ResponseCacheRecord responseCacheRecord) {
        if (responseCacheRecord == null) return;
        try {
            responseCacheRecord.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void closeQuietly(Response response) {
        if (response == null) return;
        try {
            response.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static HttpCachePolicy.FetchStrategy fetchStrategy(Request object) {
        void var0_2;
        String string2 = ((Request)object).header("X-BUY3-SDK-CACHE-FETCH-STRATEGY");
        if (string2 == null || string2.isEmpty()) {
            Object var0_1 = null;
            return var0_2;
        } else {
            HttpCachePolicy.FetchStrategy[] arrfetchStrategy = HttpCachePolicy.FetchStrategy.values();
            int n = arrfetchStrategy.length;
            int n2 = 0;
            while (n2 < n) {
                HttpCachePolicy.FetchStrategy fetchStrategy;
                HttpCachePolicy.FetchStrategy fetchStrategy2 = fetchStrategy = arrfetchStrategy[n2];
                if (fetchStrategy.name().equals(string2)) return var0_2;
                ++n2;
            }
            return null;
        }
    }

    static boolean isNetworkFirst(Request object) {
        return (object = Utils.fetchStrategy(object)) == HttpCachePolicy.FetchStrategy.NETWORK_ONLY || object == HttpCachePolicy.FetchStrategy.NETWORK_FIRST;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isStale(Request object, Response object2) {
        block5: {
            block4: {
                if (Utils.fetchStrategy((Request)object) == HttpCachePolicy.FetchStrategy.CACHE_ONLY) break block4;
                object = ((Request)object).header("X-BUY3-SDK-EXPIRE-TIMEOUT");
                if ((object2 = ((Response)object2).header("X-BUY3-SDK-SERVED-DATE")) == null || object == null) {
                    return true;
                }
                long l = Long.parseLong((String)object);
                object = HttpDate.parse((String)object2);
                long l2 = System.currentTimeMillis();
                if (object == null || l2 - ((Date)object).getTime() > l) break block5;
            }
            return false;
        }
        return true;
    }

    static boolean shouldSkipCache(Request object) {
        HttpCachePolicy.FetchStrategy fetchStrategy = Utils.fetchStrategy((Request)object);
        object = ((Request)object).header("X-BUY3-SDK-CACHE-KEY");
        return fetchStrategy == null || object == null || ((String)object).isEmpty();
    }

    static boolean shouldSkipNetwork(Request request) {
        return Utils.fetchStrategy(request) == HttpCachePolicy.FetchStrategy.CACHE_ONLY;
    }

    static Response strip(Response response) {
        Response response2 = response;
        if (response != null) {
            response2 = response;
            if (response.body() != null) {
                response2 = response.newBuilder().body(null).networkResponse(null).cacheResponse(null).build();
            }
        }
        return response2;
    }

    static Response unsatisfiableCacheRequest(Request request) {
        return new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (cache-only)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
    }

    static Response withServedDateHeader(Response response) throws IOException {
        return response.newBuilder().addHeader("X-BUY3-SDK-SERVED-DATE", HttpDate.format(new Date())).build();
    }
}

