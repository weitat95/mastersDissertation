/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import timber.log.Timber;

final class CacheResponseBody
extends ResponseBody {
    private final String contentLength;
    private final String contentType;
    private BufferedSource responseBodySource;

    CacheResponseBody(Source source, String string2, String string3) {
        this.responseBodySource = Okio.buffer(source);
        this.contentType = string2;
        this.contentLength = string3;
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
        return this.responseBodySource;
    }
}

