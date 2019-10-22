/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.CacheResponseBody;
import com.shopify.buy3.cache.DiskLruCacheStore;
import com.shopify.buy3.cache.HttpCacheInterceptor;
import com.shopify.buy3.cache.ResponseBodyProxy;
import com.shopify.buy3.cache.ResponseCacheRecord;
import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import com.shopify.buy3.cache.ResponseCacheStore;
import com.shopify.buy3.cache.ResponseHeaderRecord;
import com.shopify.buy3.cache.Utils;
import java.io.File;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Source;
import timber.log.Timber;

public final class HttpCache {
    private final ResponseCacheStore cacheStore;

    HttpCache(ResponseCacheStore responseCacheStore) {
        this.cacheStore = responseCacheStore;
    }

    public HttpCache(File file, long l) {
        this(new DiskLruCacheStore(Utils.checkNotNull(file, "directory == null"), l));
    }

    public static String cacheKey(RequestBody object) {
        try {
            Buffer buffer = new Buffer();
            ((RequestBody)object).writeTo(buffer);
            object = buffer.readByteString().md5().hex();
            return object;
        }
        catch (Exception exception) {
            Timber.w(exception, "failed to resolve cache key", new Object[0]);
            return "";
        }
    }

    public Interceptor httpInterceptor() {
        return new HttpCacheInterceptor(this);
    }

    Response proxyResponse(Response response, String object) {
        block4: {
            ResponseCacheRecordEditor responseCacheRecordEditor;
            ResponseCacheRecordEditor responseCacheRecordEditor2 = null;
            try {
                responseCacheRecordEditor = this.cacheStore.cacheRecordEditor((String)object);
                object = response;
                if (responseCacheRecordEditor == null) break block4;
                responseCacheRecordEditor2 = responseCacheRecordEditor;
            }
            catch (Exception exception) {
                Utils.abortQuietly(responseCacheRecordEditor2);
                Timber.w(exception, "failed to proxy response", new Object[0]);
                return response;
            }
            new ResponseHeaderRecord(response).writeTo(responseCacheRecordEditor);
            responseCacheRecordEditor2 = responseCacheRecordEditor;
            object = response.newBuilder().body(new ResponseBodyProxy(responseCacheRecordEditor, response)).build();
        }
        return object;
    }

    Response read(String string2) {
        Object object;
        ResponseCacheRecord responseCacheRecord = null;
        try {
            object = this.cacheStore.cacheRecord(string2);
            if (object == null) {
                return null;
            }
            responseCacheRecord = object;
        }
        catch (Exception exception) {
            Utils.closeQuietly(responseCacheRecord);
            Timber.w(exception, "failed to read cached response by key: %s", string2);
            return null;
        }
        ForwardingSource forwardingSource = new ForwardingSource(object.bodySource(), (ResponseCacheRecord)object){
            final /* synthetic */ ResponseCacheRecord val$cacheRecord;
            {
                this.val$cacheRecord = responseCacheRecord;
                super(source);
            }

            @Override
            public void close() throws IOException {
                super.close();
                Utils.closeQuietly(this.val$cacheRecord);
            }
        };
        responseCacheRecord = object;
        Response response = new ResponseHeaderRecord(object.headerSource()).response();
        responseCacheRecord = object;
        String string3 = response.header("Content-Type");
        responseCacheRecord = object;
        String string4 = response.header("Content-Length");
        responseCacheRecord = object;
        object = response.newBuilder().body(new CacheResponseBody(forwardingSource, string3, string4)).build();
        return object;
    }

    public void removeQuietly(String string2) {
        try {
            this.cacheStore.remove(string2);
            return;
        }
        catch (IOException iOException) {
            Timber.w(iOException, "failed to remove cached response by key: %s", string2);
            return;
        }
    }

}

