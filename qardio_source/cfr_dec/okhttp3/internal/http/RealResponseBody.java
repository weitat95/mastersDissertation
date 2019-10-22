/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.BufferedSource;

public final class RealResponseBody
extends ResponseBody {
    private final Headers headers;
    private final BufferedSource source;

    public RealResponseBody(Headers headers, BufferedSource bufferedSource) {
        this.headers = headers;
        this.source = bufferedSource;
    }

    @Override
    public long contentLength() {
        return HttpHeaders.contentLength(this.headers);
    }

    @Override
    public MediaType contentType() {
        String string2 = this.headers.get("Content-Type");
        if (string2 != null) {
            return MediaType.parse(string2);
        }
        return null;
    }

    @Override
    public BufferedSource source() {
        return this.source;
    }
}

