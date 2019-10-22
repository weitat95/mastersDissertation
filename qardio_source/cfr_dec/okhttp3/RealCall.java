/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Connection;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

final class RealCall
implements Call {
    final OkHttpClient client;
    private boolean executed;
    final boolean forWebSocket;
    final Request originalRequest;
    final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

    RealCall(OkHttpClient okHttpClient, Request request, boolean bl) {
        this.client = okHttpClient;
        this.originalRequest = request;
        this.forWebSocket = bl;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(okHttpClient, bl);
    }

    private void captureCallStackTrace() {
        Object object = Platform.get().getStackTraceForCloseable("response.body().close()");
        this.retryAndFollowUpInterceptor.setCallStackTrace(object);
    }

    @Override
    public void cancel() {
        this.retryAndFollowUpInterceptor.cancel();
    }

    @Override
    public RealCall clone() {
        return new RealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.captureCallStackTrace();
        this.client.dispatcher().enqueue(new AsyncCall(callback));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.captureCallStackTrace();
        try {
            this.client.dispatcher().executed(this);
            Response response = this.getResponseWithInterceptorChain();
            if (response != null) return response;
            throw new IOException("Canceled");
        }
        finally {
            this.client.dispatcher().finished(this);
        }
    }

    Response getResponseWithInterceptorChain() throws IOException {
        ArrayList<Interceptor> arrayList = new ArrayList<Interceptor>();
        arrayList.addAll(this.client.interceptors());
        arrayList.add(this.retryAndFollowUpInterceptor);
        arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList.add(new CacheInterceptor(this.client.internalCache()));
        arrayList.add(new ConnectInterceptor(this.client));
        if (!this.forWebSocket) {
            arrayList.addAll(this.client.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.forWebSocket));
        return new RealInterceptorChain(arrayList, null, null, null, 0, this.originalRequest).proceed(this.originalRequest);
    }

    public boolean isCanceled() {
        return this.retryAndFollowUpInterceptor.isCanceled();
    }

    String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    @Override
    public Request request() {
        return this.originalRequest;
    }

    /*
     * Enabled aggressive block sorting
     */
    String toLoggableString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.isCanceled() ? "canceled " : "";
        stringBuilder = stringBuilder.append(string2);
        if (this.forWebSocket) {
            string2 = "web socket";
            return stringBuilder.append(string2).append(" to ").append(this.redactedUrl()).toString();
        }
        string2 = "call";
        return stringBuilder.append(string2).append(" to ").append(this.redactedUrl()).toString();
    }

    final class AsyncCall
    extends NamedRunnable {
        private final Callback responseCallback;

        AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.redactedUrl());
            this.responseCallback = callback;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected void execute() {
            boolean bl;
            boolean bl2 = bl = false;
            try {
                Response response = RealCall.this.getResponseWithInterceptorChain();
                bl2 = bl;
                if (RealCall.this.retryAndFollowUpInterceptor.isCanceled()) {
                    bl2 = true;
                    this.responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                    do {
                        return;
                        break;
                    } while (true);
                }
                bl2 = true;
                this.responseCallback.onResponse(RealCall.this, response);
                return;
            }
            catch (IOException iOException) {
                if (bl2) {
                    Platform.get().log(4, "Callback failure for " + RealCall.this.toLoggableString(), iOException);
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.responseCallback.onFailure(RealCall.this, iOException);
                return;
            }
            finally {
                RealCall.this.client.dispatcher().finished(this);
            }
        }

        String host() {
            return RealCall.this.originalRequest.url().host();
        }
    }

}

