/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Handshake;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealInterceptorChain;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public final class CallServerInterceptor
implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean bl) {
        this.forWebSocket = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        HttpCodec httpCodec = ((RealInterceptorChain)object).httpStream();
        StreamAllocation streamAllocation = ((RealInterceptorChain)object).streamAllocation();
        Request request = object.request();
        long l = System.currentTimeMillis();
        httpCodec.writeRequestHeaders(request);
        Object var6_6 = null;
        Object object2 = null;
        object = var6_6;
        if (HttpMethod.permitsRequestBody(request.method())) {
            object = var6_6;
            if (request.body() != null) {
                if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
                    httpCodec.flushRequest();
                    object2 = httpCodec.readResponseHeaders(true);
                }
                object = object2;
                if (object2 == null) {
                    object = Okio.buffer(httpCodec.createRequestBody(request, request.body().contentLength()));
                    request.body().writeTo((BufferedSink)object);
                    object.close();
                    object = object2;
                }
            }
        }
        httpCodec.finishRequest();
        object2 = object;
        if (object == null) {
            object2 = httpCodec.readResponseHeaders(false);
        }
        object = ((Response.Builder)object2).request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
        int n = ((Response)object).code();
        object = this.forWebSocket && n == 101 ? ((Response)object).newBuilder().body(Util.EMPTY_RESPONSE).build() : ((Response)object).newBuilder().body(httpCodec.openResponseBody((Response)object)).build();
        if ("close".equalsIgnoreCase(((Response)object).request().header("Connection")) || "close".equalsIgnoreCase(((Response)object).header("Connection"))) {
            streamAllocation.noNewStreams();
        }
        if ((n == 204 || n == 205) && ((Response)object).body().contentLength() > 0L) {
            throw new ProtocolException("HTTP " + n + " had non-zero Content-Length: " + ((Response)object).body().contentLength());
        }
        return object;
    }
}

