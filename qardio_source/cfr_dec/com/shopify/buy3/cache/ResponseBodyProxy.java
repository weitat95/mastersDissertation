/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.ResponseBodyCacheSink;
import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import com.shopify.buy3.cache.Utils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import timber.log.Timber;

final class ResponseBodyProxy
extends ResponseBody {
    private final String contentLength;
    private final String contentType;
    private final Source responseBodySource;

    ResponseBodyProxy(ResponseCacheRecordEditor responseCacheRecordEditor, Response response) {
        this.contentType = response.header("Content-Type");
        this.contentLength = response.header("Content-Length");
        this.responseBodySource = new ProxySource(responseCacheRecordEditor, response.body().source());
    }

    @Override
    public long contentLength() {
        long l = -1L;
        try {
            if (this.contentLength != null) {
                l = Long.parseLong(this.contentLength);
            }
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            Timber.w(numberFormatException, "failed to parse content length", new Object[0]);
            return -1L;
        }
    }

    @Override
    public MediaType contentType() {
        if (this.contentType != null) {
            return MediaType.parse(this.contentType);
        }
        return null;
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(this.responseBodySource);
    }

    private static class ProxySource
    implements Source {
        final ResponseCacheRecordEditor cacheRecordEditor;
        boolean closed;
        final ResponseBodyCacheSink responseBodyCacheSink;
        final Source responseBodySource;

        ProxySource(ResponseCacheRecordEditor responseCacheRecordEditor, Source source) {
            this.cacheRecordEditor = responseCacheRecordEditor;
            this.responseBodySource = source;
            this.responseBodyCacheSink = new ResponseBodyCacheSink(Okio.buffer(responseCacheRecordEditor.bodySink())){

                @Override
                void onException(Exception exception) {
                    ProxySource.this.abortCacheQuietly();
                    Timber.w(exception, "failed to write to cache response sink", new Object[0]);
                }
            };
        }

        private void abortCacheQuietly() {
            Utils.closeQuietly(this.responseBodyCacheSink);
            try {
                this.cacheRecordEditor.abort();
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        private void commitCache() {
            try {
                this.responseBodyCacheSink.close();
                this.cacheRecordEditor.commit();
                return;
            }
            catch (Exception exception) {
                Utils.closeQuietly(this.responseBodyCacheSink);
                this.abortCacheQuietly();
                Timber.w(exception, "failed to commit cache response", new Object[0]);
                return;
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.responseBodySource.close();
                this.commitCache();
                return;
            }
            this.responseBodySource.close();
            this.abortCacheQuietly();
        }

        @Override
        public long read(Buffer buffer, long l) throws IOException {
            try {
                l = this.responseBodySource.read(buffer, l);
                if (l == -1L) {
                    if (!this.closed) {
                        this.closed = true;
                        this.commitCache();
                    }
                    return -1L;
                }
            }
            catch (IOException iOException) {
                if (!this.closed) {
                    this.closed = true;
                    this.abortCacheQuietly();
                }
                throw iOException;
            }
            this.responseBodyCacheSink.copyFrom(buffer, buffer.size() - l, l);
            return l;
        }

        @Override
        public Timeout timeout() {
            return this.responseBodySource.timeout();
        }

    }

}

