/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import okhttp3.Address;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;

public final class RealInterceptorChain
implements Interceptor.Chain {
    private int calls;
    private final Connection connection;
    private final HttpCodec httpCodec;
    private final int index;
    private final List<Interceptor> interceptors;
    private final Request request;
    private final StreamAllocation streamAllocation;

    public RealInterceptorChain(List<Interceptor> list, StreamAllocation streamAllocation, HttpCodec httpCodec, Connection connection, int n, Request request) {
        this.interceptors = list;
        this.connection = connection;
        this.streamAllocation = streamAllocation;
        this.httpCodec = httpCodec;
        this.index = n;
        this.request = request;
    }

    private boolean sameConnection(HttpUrl httpUrl) {
        return httpUrl.host().equals(this.connection.route().address().url().host()) && httpUrl.port() == this.connection.route().address().url().port();
    }

    @Override
    public Connection connection() {
        return this.connection;
    }

    public HttpCodec httpStream() {
        return this.httpCodec;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        return this.proceed(request, this.streamAllocation, this.httpCodec, this.connection);
    }

    public Response proceed(Request object, StreamAllocation object2, HttpCodec httpCodec, Connection object3) throws IOException {
        if (this.index >= this.interceptors.size()) {
            throw new AssertionError();
        }
        ++this.calls;
        if (this.httpCodec != null && !this.sameConnection(((Request)object).url())) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must retain the same host and port");
        }
        if (this.httpCodec != null && this.calls > 1) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must call proceed() exactly once");
        }
        object = new RealInterceptorChain(this.interceptors, (StreamAllocation)object2, httpCodec, (Connection)object3, this.index + 1, (Request)object);
        object2 = this.interceptors.get(this.index);
        object3 = object2.intercept((Interceptor.Chain)object);
        if (httpCodec != null && this.index + 1 < this.interceptors.size() && ((RealInterceptorChain)object).calls != 1) {
            throw new IllegalStateException("network interceptor " + object2 + " must call proceed() exactly once");
        }
        if (object3 == null) {
            throw new NullPointerException("interceptor " + object2 + " returned null");
        }
        return object3;
    }

    @Override
    public Request request() {
        return this.request;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }
}

