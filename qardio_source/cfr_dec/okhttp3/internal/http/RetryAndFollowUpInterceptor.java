/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.UnrepeatableRequestBody;
import okhttp3.internal.http2.ConnectionShutdownException;

public final class RetryAndFollowUpInterceptor
implements Interceptor {
    private Object callStackTrace;
    private volatile boolean canceled;
    private final OkHttpClient client;
    private final boolean forWebSocket;
    private StreamAllocation streamAllocation;

    public RetryAndFollowUpInterceptor(OkHttpClient okHttpClient, boolean bl) {
        this.client = okHttpClient;
        this.forWebSocket = bl;
    }

    private Address createAddress(HttpUrl httpUrl) {
        SSLSocketFactory sSLSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (httpUrl.isHttps()) {
            sSLSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        }
        return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Request followUpRequest(Response response) throws IOException {
        if (response == null) {
            throw new IllegalStateException();
        }
        Object object = this.streamAllocation.connection();
        object = object != null ? object.route() : null;
        int n = response.code();
        Object object2 = response.request().method();
        switch (n) {
            default: {
                return null;
            }
            case 407: {
                object2 = object != null ? ((Route)object).proxy() : this.client.proxy();
                if (((Proxy)object2).type() == Proxy.Type.HTTP) return this.client.proxyAuthenticator().authenticate((Route)object, response);
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            }
            case 401: {
                return this.client.authenticator().authenticate((Route)object, response);
            }
            case 307: 
            case 308: {
                if (!((String)object2).equals("GET")) {
                    if (!((String)object2).equals("HEAD")) return null;
                }
            }
            case 300: 
            case 301: 
            case 302: 
            case 303: {
                if (!this.client.followRedirects()) return null;
                object = response.header("Location");
                if (object == null) return null;
                HttpUrl httpUrl = response.request().url().resolve((String)object);
                if (httpUrl == null) return null;
                if (!httpUrl.scheme().equals(response.request().url().scheme())) {
                    if (!this.client.followSslRedirects()) return null;
                }
                Request.Builder builder = response.request().newBuilder();
                if (HttpMethod.permitsRequestBody((String)object2)) {
                    boolean bl = HttpMethod.redirectsWithBody((String)object2);
                    if (HttpMethod.redirectsToGet((String)object2)) {
                        builder.method("GET", null);
                    } else {
                        object = bl ? response.request().body() : null;
                        builder.method((String)object2, (RequestBody)object);
                    }
                    if (!bl) {
                        builder.removeHeader("Transfer-Encoding");
                        builder.removeHeader("Content-Length");
                        builder.removeHeader("Content-Type");
                    }
                }
                if (this.sameConnection(response, httpUrl)) return builder.url(httpUrl).build();
                builder.removeHeader("Authorization");
                return builder.url(httpUrl).build();
            }
            case 408: 
        }
        if (response.request().body() instanceof UnrepeatableRequestBody) return null;
        return response.request();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isRecoverable(IOException iOException, boolean bl) {
        boolean bl2 = true;
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            if (!(iOException instanceof SocketTimeoutException)) return false;
            if (bl) return false;
            return bl2;
        }
        if (iOException instanceof SSLHandshakeException) {
            if (iOException.getCause() instanceof CertificateException) return false;
        }
        if (iOException instanceof SSLPeerUnverifiedException) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean recover(IOException iOException, boolean bl, Request request) {
        this.streamAllocation.streamFailed(iOException);
        return this.client.retryOnConnectionFailure() && (!bl || !(request.body() instanceof UnrepeatableRequestBody)) && this.isRecoverable(iOException, bl) && this.streamAllocation.hasMoreRoutes();
    }

    private boolean sameConnection(Response object, HttpUrl httpUrl) {
        return ((HttpUrl)(object = ((Response)object).request().url())).host().equals(httpUrl.host()) && ((HttpUrl)object).port() == httpUrl.port() && ((HttpUrl)object).scheme().equals(httpUrl.scheme());
    }

    public void cancel() {
        this.canceled = true;
        StreamAllocation streamAllocation = this.streamAllocation;
        if (streamAllocation != null) {
            streamAllocation.cancel();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Response intercept(Interceptor.Chain var1_1) throws IOException {
        var4_3 = var1_1.request();
        this.streamAllocation = new StreamAllocation(this.client.connectionPool(), this.createAddress(var4_3.url()), this.callStackTrace);
        var2_4 = 0;
        var5_5 = null;
        do lbl-1000:
        // 6 sources
        {
            block16: {
                block15: {
                    if (this.canceled) {
                        this.streamAllocation.release();
                        throw new IOException("Canceled");
                    }
                    try {
                        var6_7 = ((RealInterceptorChain)var1_1).proceed((Request)var4_3, this.streamAllocation, null, null);
                        var4_3 = var6_7;
                        if (var5_5 == null) break block15;
                    }
                    catch (RouteException var6_8) {
                        if (this.recover(var6_8.getLastConnectException(), false, (Request)var4_3)) ** GOTO lbl-1000
                        throw var6_8.getLastConnectException();
                    }
                    catch (IOException var6_9) {
                        var3_6 = var6_9 instanceof ConnectionShutdownException == false;
                        if (this.recover(var6_9, var3_6, (Request)var4_3)) ** GOTO lbl-1000
                        throw var6_9;
                    }
                    finally {
                        if (!false) ** GOTO lbl-1000
                        this.streamAllocation.streamFailed(null);
                        this.streamAllocation.release();
                    }
                    var4_3 = var6_7.newBuilder().priorResponse(var5_5.newBuilder().body(null).build()).build();
                }
                if ((var6_7 = this.followUpRequest((Response)var4_3)) == null) {
                    if (this.forWebSocket != false) return var4_3;
                    this.streamAllocation.release();
                    return var4_3;
                }
                break block16;
                continue;
            }
            Util.closeQuietly(var4_3.body());
            if (++var2_4 > 20) {
                this.streamAllocation.release();
                throw new ProtocolException("Too many follow-up requests: " + var2_4);
            }
            if (var6_7.body() instanceof UnrepeatableRequestBody) {
                this.streamAllocation.release();
                throw new HttpRetryException("Cannot retry streamed HTTP body", var4_3.code());
            }
            if (!this.sameConnection((Response)var4_3, var6_7.url())) {
                this.streamAllocation.release();
                this.streamAllocation = new StreamAllocation(this.client.connectionPool(), this.createAddress(var6_7.url()), this.callStackTrace);
            } else if (this.streamAllocation.codec() != null) {
                throw new IllegalStateException("Closing the body of " + var4_3 + " didn't close its backing stream. Bad interceptor?");
            }
            var5_5 = var4_3;
            var4_3 = var6_7;
        } while (true);
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCallStackTrace(Object object) {
        this.callStackTrace = object;
    }
}

