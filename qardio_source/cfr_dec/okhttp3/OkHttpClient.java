/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.RealCall;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;

public class OkHttpClient
implements Cloneable,
Call.Factory {
    static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
    static final List<Protocol> DEFAULT_PROTOCOLS;
    final Authenticator authenticator;
    final Cache cache;
    final CertificateChainCleaner certificateChainCleaner;
    final CertificatePinner certificatePinner;
    final int connectTimeout;
    final ConnectionPool connectionPool;
    final List<ConnectionSpec> connectionSpecs;
    final CookieJar cookieJar;
    final Dispatcher dispatcher;
    final Dns dns;
    final boolean followRedirects;
    final boolean followSslRedirects;
    final HostnameVerifier hostnameVerifier;
    final List<Interceptor> interceptors;
    final InternalCache internalCache;
    final List<Interceptor> networkInterceptors;
    final int pingInterval;
    final List<Protocol> protocols;
    final Proxy proxy;
    final Authenticator proxyAuthenticator;
    final ProxySelector proxySelector;
    final int readTimeout;
    final boolean retryOnConnectionFailure;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final int writeTimeout;

    static {
        DEFAULT_PROTOCOLS = Util.immutableList(new Protocol[]{Protocol.HTTP_2, Protocol.HTTP_1_1});
        DEFAULT_CONNECTION_SPECS = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);
        Internal.instance = new Internal(){

            @Override
            public void addLenient(Headers.Builder builder, String string2) {
                builder.addLenient(string2);
            }

            @Override
            public void addLenient(Headers.Builder builder, String string2, String string3) {
                builder.addLenient(string2, string3);
            }

            @Override
            public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean bl) {
                connectionSpec.apply(sSLSocket, bl);
            }

            @Override
            public int code(Response.Builder builder) {
                return builder.code;
            }

            @Override
            public boolean connectionBecameIdle(ConnectionPool connectionPool, RealConnection realConnection) {
                return connectionPool.connectionBecameIdle(realConnection);
            }

            @Override
            public Socket deduplicate(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation) {
                return connectionPool.deduplicate(address, streamAllocation);
            }

            @Override
            public RealConnection get(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation) {
                return connectionPool.get(address, streamAllocation);
            }

            @Override
            public void put(ConnectionPool connectionPool, RealConnection realConnection) {
                connectionPool.put(realConnection);
            }

            @Override
            public RouteDatabase routeDatabase(ConnectionPool connectionPool) {
                return connectionPool.routeDatabase;
            }
        };
    }

    public OkHttpClient() {
        this(new Builder());
    }

    /*
     * Enabled aggressive block sorting
     */
    OkHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.proxy = builder.proxy;
        this.protocols = builder.protocols;
        this.connectionSpecs = builder.connectionSpecs;
        this.interceptors = Util.immutableList(builder.interceptors);
        this.networkInterceptors = Util.immutableList(builder.networkInterceptors);
        this.proxySelector = builder.proxySelector;
        this.cookieJar = builder.cookieJar;
        this.cache = builder.cache;
        this.internalCache = builder.internalCache;
        this.socketFactory = builder.socketFactory;
        boolean bl = false;
        for (ConnectionSpec connectionSpec : this.connectionSpecs) {
            if (bl || connectionSpec.isTls()) {
                bl = true;
                continue;
            }
            bl = false;
        }
        if (builder.sslSocketFactory != null || !bl) {
            this.sslSocketFactory = builder.sslSocketFactory;
            this.certificateChainCleaner = builder.certificateChainCleaner;
        } else {
            X509TrustManager x509TrustManager = this.systemDefaultTrustManager();
            this.sslSocketFactory = this.systemDefaultSslSocketFactory(x509TrustManager);
            this.certificateChainCleaner = CertificateChainCleaner.get(x509TrustManager);
        }
        this.hostnameVerifier = builder.hostnameVerifier;
        this.certificatePinner = builder.certificatePinner.withCertificateChainCleaner(this.certificateChainCleaner);
        this.proxyAuthenticator = builder.proxyAuthenticator;
        this.authenticator = builder.authenticator;
        this.connectionPool = builder.connectionPool;
        this.dns = builder.dns;
        this.followSslRedirects = builder.followSslRedirects;
        this.followRedirects = builder.followRedirects;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.pingInterval = builder.pingInterval;
    }

    private SSLSocketFactory systemDefaultSslSocketFactory(X509TrustManager object) {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{object}, null);
            object = sSLContext.getSocketFactory();
            return object;
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new AssertionError();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private X509TrustManager systemDefaultTrustManager() {
        try {
            Object object = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            object.init((KeyStore)null);
            object = object.getTrustManagers();
            if (((TrustManager[])object).length != 1) throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(object));
            if (object[0] instanceof X509TrustManager) return (X509TrustManager)object[0];
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(object));
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new AssertionError();
        }
    }

    public Authenticator authenticator() {
        return this.authenticator;
    }

    public CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    public int connectTimeoutMillis() {
        return this.connectTimeout;
    }

    public ConnectionPool connectionPool() {
        return this.connectionPool;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    public CookieJar cookieJar() {
        return this.cookieJar;
    }

    public Dispatcher dispatcher() {
        return this.dispatcher;
    }

    public Dns dns() {
        return this.dns;
    }

    public boolean followRedirects() {
        return this.followRedirects;
    }

    public boolean followSslRedirects() {
        return this.followSslRedirects;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<Interceptor> interceptors() {
        return this.interceptors;
    }

    InternalCache internalCache() {
        if (this.cache != null) {
            return this.cache.internalCache;
        }
        return this.internalCache;
    }

    public List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public Call newCall(Request request) {
        return new RealCall(this, request, false);
    }

    public List<Protocol> protocols() {
        return this.protocols;
    }

    public Proxy proxy() {
        return this.proxy;
    }

    public Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    public ProxySelector proxySelector() {
        return this.proxySelector;
    }

    public int readTimeoutMillis() {
        return this.readTimeout;
    }

    public boolean retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    public SocketFactory socketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.sslSocketFactory;
    }

    public int writeTimeoutMillis() {
        return this.writeTimeout;
    }

    public static final class Builder {
        Authenticator authenticator;
        Cache cache;
        CertificateChainCleaner certificateChainCleaner;
        CertificatePinner certificatePinner;
        int connectTimeout;
        ConnectionPool connectionPool;
        List<ConnectionSpec> connectionSpecs;
        CookieJar cookieJar;
        Dispatcher dispatcher;
        Dns dns;
        boolean followRedirects;
        boolean followSslRedirects;
        HostnameVerifier hostnameVerifier;
        final List<Interceptor> interceptors = new ArrayList<Interceptor>();
        InternalCache internalCache;
        final List<Interceptor> networkInterceptors = new ArrayList<Interceptor>();
        int pingInterval;
        List<Protocol> protocols;
        Proxy proxy;
        Authenticator proxyAuthenticator;
        ProxySelector proxySelector;
        int readTimeout;
        boolean retryOnConnectionFailure;
        SocketFactory socketFactory;
        SSLSocketFactory sslSocketFactory;
        int writeTimeout;

        public Builder() {
            this.dispatcher = new Dispatcher();
            this.protocols = DEFAULT_PROTOCOLS;
            this.connectionSpecs = DEFAULT_CONNECTION_SPECS;
            this.proxySelector = ProxySelector.getDefault();
            this.cookieJar = CookieJar.NO_COOKIES;
            this.socketFactory = SocketFactory.getDefault();
            this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
            this.certificatePinner = CertificatePinner.DEFAULT;
            this.proxyAuthenticator = Authenticator.NONE;
            this.authenticator = Authenticator.NONE;
            this.connectionPool = new ConnectionPool();
            this.dns = Dns.SYSTEM;
            this.followSslRedirects = true;
            this.followRedirects = true;
            this.retryOnConnectionFailure = true;
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
            this.writeTimeout = 10000;
            this.pingInterval = 0;
        }

        Builder(OkHttpClient okHttpClient) {
            this.dispatcher = okHttpClient.dispatcher;
            this.proxy = okHttpClient.proxy;
            this.protocols = okHttpClient.protocols;
            this.connectionSpecs = okHttpClient.connectionSpecs;
            this.interceptors.addAll(okHttpClient.interceptors);
            this.networkInterceptors.addAll(okHttpClient.networkInterceptors);
            this.proxySelector = okHttpClient.proxySelector;
            this.cookieJar = okHttpClient.cookieJar;
            this.internalCache = okHttpClient.internalCache;
            this.cache = okHttpClient.cache;
            this.socketFactory = okHttpClient.socketFactory;
            this.sslSocketFactory = okHttpClient.sslSocketFactory;
            this.certificateChainCleaner = okHttpClient.certificateChainCleaner;
            this.hostnameVerifier = okHttpClient.hostnameVerifier;
            this.certificatePinner = okHttpClient.certificatePinner;
            this.proxyAuthenticator = okHttpClient.proxyAuthenticator;
            this.authenticator = okHttpClient.authenticator;
            this.connectionPool = okHttpClient.connectionPool;
            this.dns = okHttpClient.dns;
            this.followSslRedirects = okHttpClient.followSslRedirects;
            this.followRedirects = okHttpClient.followRedirects;
            this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure;
            this.connectTimeout = okHttpClient.connectTimeout;
            this.readTimeout = okHttpClient.readTimeout;
            this.writeTimeout = okHttpClient.writeTimeout;
            this.pingInterval = okHttpClient.pingInterval;
        }

        private static int checkDuration(String string2, long l, TimeUnit timeUnit) {
            if (l < 0L) {
                throw new IllegalArgumentException(string2 + " < 0");
            }
            if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            }
            long l2 = timeUnit.toMillis(l);
            if (l2 > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(string2 + " too large.");
            }
            if (l2 == 0L && l > 0L) {
                throw new IllegalArgumentException(string2 + " too small.");
            }
            return (int)l2;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            this.networkInterceptors.add(interceptor);
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }

        public Builder certificatePinner(CertificatePinner certificatePinner) {
            if (certificatePinner == null) {
                throw new NullPointerException("certificatePinner == null");
            }
            this.certificatePinner = certificatePinner;
            return this;
        }

        public Builder connectTimeout(long l, TimeUnit timeUnit) {
            this.connectTimeout = Builder.checkDuration("timeout", l, timeUnit);
            return this;
        }

        public Builder readTimeout(long l, TimeUnit timeUnit) {
            this.readTimeout = Builder.checkDuration("timeout", l, timeUnit);
            return this;
        }

        public Builder writeTimeout(long l, TimeUnit timeUnit) {
            this.writeTimeout = Builder.checkDuration("timeout", l, timeUnit);
            return this;
        }
    }

}

