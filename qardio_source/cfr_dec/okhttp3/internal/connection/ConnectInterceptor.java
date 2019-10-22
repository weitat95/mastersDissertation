/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.RealInterceptorChain;

public final class ConnectInterceptor
implements Interceptor {
    public final OkHttpClient client;

    public ConnectInterceptor(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        boolean bl;
        chain = (RealInterceptorChain)chain;
        Request request = ((RealInterceptorChain)chain).request();
        StreamAllocation streamAllocation = ((RealInterceptorChain)chain).streamAllocation();
        if (!request.method().equals("GET")) {
            bl = true;
            do {
                return ((RealInterceptorChain)chain).proceed(request, streamAllocation, streamAllocation.newStream(this.client, bl), streamAllocation.connection());
                break;
            } while (true);
        }
        bl = false;
        return ((RealInterceptorChain)chain).proceed(request, streamAllocation, streamAllocation.newStream(this.client, bl), streamAllocation.connection());
    }
}

