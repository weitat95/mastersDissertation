/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

public final class BridgeInterceptor
implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    private String cookieHeader(List<Cookie> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            Cookie cookie = list.get(i);
            stringBuilder.append(cookie.name()).append('=').append(cookie.value());
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        boolean bl;
        Object object2 = object.request();
        Object object3 = ((Request)object2).newBuilder();
        Object object4 = ((Request)object2).body();
        if (object4 != null) {
            long l;
            MediaType mediaType = ((RequestBody)object4).contentType();
            if (mediaType != null) {
                ((Request.Builder)object3).header("Content-Type", mediaType.toString());
            }
            if ((l = ((RequestBody)object4).contentLength()) != -1L) {
                ((Request.Builder)object3).header("Content-Length", Long.toString(l));
                ((Request.Builder)object3).removeHeader("Transfer-Encoding");
            } else {
                ((Request.Builder)object3).header("Transfer-Encoding", "chunked");
                ((Request.Builder)object3).removeHeader("Content-Length");
            }
        }
        if (((Request)object2).header("Host") == null) {
            ((Request.Builder)object3).header("Host", Util.hostHeader(((Request)object2).url(), false));
        }
        if (((Request)object2).header("Connection") == null) {
            ((Request.Builder)object3).header("Connection", "Keep-Alive");
        }
        boolean bl2 = bl = false;
        if (((Request)object2).header("Accept-Encoding") == null) {
            bl2 = bl;
            if (((Request)object2).header("Range") == null) {
                bl2 = true;
                ((Request.Builder)object3).header("Accept-Encoding", "gzip");
            }
        }
        if (!(object4 = this.cookieJar.loadForRequest(((Request)object2).url())).isEmpty()) {
            ((Request.Builder)object3).header("Cookie", this.cookieHeader((List<Cookie>)object4));
        }
        if (((Request)object2).header("User-Agent") == null) {
            ((Request.Builder)object3).header("User-Agent", Version.userAgent());
        }
        object = object.proceed(((Request.Builder)object3).build());
        HttpHeaders.receiveHeaders(this.cookieJar, ((Request)object2).url(), ((Response)object).headers());
        object2 = ((Response)object).newBuilder().request((Request)object2);
        if (bl2 && "gzip".equalsIgnoreCase(((Response)object).header("Content-Encoding")) && HttpHeaders.hasBody((Response)object)) {
            object3 = new GzipSource(((Response)object).body().source());
            object = ((Response)object).headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
            ((Response.Builder)object2).headers((Headers)object);
            ((Response.Builder)object2).body(new RealResponseBody((Headers)object, Okio.buffer((Source)object3)));
        }
        return ((Response.Builder)object2).build();
    }
}

