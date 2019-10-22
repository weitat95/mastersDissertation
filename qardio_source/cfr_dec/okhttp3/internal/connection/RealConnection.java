/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.connection.ConnectionSpecSelector;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http1.Http1Codec;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Codec;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;

public final class RealConnection
extends Http2Connection.Listener
implements Connection {
    public int allocationLimit = 1;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList<Reference<StreamAllocation>>();
    private final ConnectionPool connectionPool;
    private Handshake handshake;
    private Http2Connection http2Connection;
    public long idleAtNanos = Long.MAX_VALUE;
    public boolean noNewStreams;
    private Protocol protocol;
    private Socket rawSocket;
    private final Route route;
    private BufferedSink sink;
    private Socket socket;
    private BufferedSource source;
    public int successCount;

    public RealConnection(ConnectionPool connectionPool, Route route) {
        this.connectionPool = connectionPool;
        this.route = route;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void connectSocket(int n, int n2) throws IOException {
        Object object = this.route.proxy();
        Object object2 = this.route.address();
        object = ((Proxy)object).type() == Proxy.Type.DIRECT || ((Proxy)object).type() == Proxy.Type.HTTP ? ((Address)object2).socketFactory().createSocket() : new Socket((Proxy)object);
        this.rawSocket = object;
        this.rawSocket.setSoTimeout(n2);
        try {
            Platform.get().connectSocket(this.rawSocket, this.route.socketAddress(), n);
        }
        catch (ConnectException connectException) {
            object2 = new ConnectException("Failed to connect to " + this.route.socketAddress());
            ((Throwable)object2).initCause(connectException);
            throw object2;
        }
        this.source = Okio.buffer(Okio.source(this.rawSocket));
        this.sink = Okio.buffer(Okio.sink(this.rawSocket));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void connectTls(ConnectionSpecSelector object) throws IOException {
        AssertionError assertionError222;
        Object object2;
        Object object3;
        block16: {
            Object object4;
            block15: {
                Address address = this.route.address();
                object4 = address.sslSocketFactory();
                object3 = null;
                object2 = null;
                try {
                    object2 = object4 = (SSLSocket)((SSLSocketFactory)object4).createSocket(this.rawSocket, address.url().host(), address.url().port(), true);
                    object3 = object4;
                    object = ((ConnectionSpecSelector)object).configureSecureSocket((SSLSocket)object4);
                    object2 = object4;
                    object3 = object4;
                    if (((ConnectionSpec)object).supportsTlsExtensions()) {
                        object2 = object4;
                        object3 = object4;
                        Platform.get().configureTlsExtensions((SSLSocket)object4, address.url().host(), address.protocols());
                    }
                    object2 = object4;
                    object3 = object4;
                    ((SSLSocket)object4).startHandshake();
                    object2 = object4;
                    object3 = object4;
                    Handshake handshake = Handshake.get(((SSLSocket)object4).getSession());
                    object2 = object4;
                    object3 = object4;
                    if (!address.hostnameVerifier().verify(address.url().host(), ((SSLSocket)object4).getSession())) {
                        object2 = object4;
                        object3 = object4;
                        object = (X509Certificate)handshake.peerCertificates().get(0);
                        object2 = object4;
                        object3 = object4;
                        throw new SSLPeerUnverifiedException("Hostname " + address.url().host() + " not verified:\n    certificate: " + CertificatePinner.pin((Certificate)object) + "\n    DN: " + ((X509Certificate)object).getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames((X509Certificate)object));
                    }
                    object2 = object4;
                    object3 = object4;
                    address.certificatePinner().check(address.url().host(), handshake.peerCertificates());
                    object2 = object4;
                    object3 = object4;
                    if (((ConnectionSpec)object).supportsTlsExtensions()) {
                        object2 = object4;
                        object3 = object4;
                        object = Platform.get().getSelectedProtocol((SSLSocket)object4);
                    } else {
                        object = null;
                    }
                    object2 = object4;
                    object3 = object4;
                    this.socket = object4;
                    object2 = object4;
                    object3 = object4;
                    this.source = Okio.buffer(Okio.source(this.socket));
                    object2 = object4;
                    object3 = object4;
                    this.sink = Okio.buffer(Okio.sink(this.socket));
                    object2 = object4;
                    object3 = object4;
                    this.handshake = handshake;
                    if (object != null) {
                        object2 = object4;
                        object3 = object4;
                        object = Protocol.get((String)object);
                        break block15;
                    }
                    object2 = object4;
                    object3 = object4;
                    {
                        catch (AssertionError assertionError222) {
                            object3 = object2;
                            if (Util.isAndroidGetsocknameError(assertionError222)) {
                                object3 = object2;
                                throw new IOException((Throwable)((Object)assertionError222));
                            }
                            break block16;
                        }
                    }
                    object = Protocol.HTTP_1_1;
                }
                catch (Throwable throwable) {
                    if (object3 != null) {
                        Platform.get().afterHandshake((SSLSocket)object3);
                    }
                    if (false) throw throwable;
                    Util.closeQuietly(object3);
                    throw throwable;
                }
            }
            object2 = object4;
            object3 = object4;
            this.protocol = object;
            if (object4 != null) {
                Platform.get().afterHandshake((SSLSocket)object4);
            }
            if (true) return;
            Util.closeQuietly((Socket)object4);
            return;
        }
        object3 = object2;
        throw assertionError222;
    }

    private void connectTunnel(int n, int n2, int n3) throws IOException {
        Request request = this.createTunnelRequest();
        HttpUrl httpUrl = request.url();
        int n4 = 0;
        do {
            if (++n4 > 21) {
                throw new ProtocolException("Too many tunnel connections attempted: " + 21);
            }
            this.connectSocket(n, n2);
            request = this.createTunnel(n2, n3, request, httpUrl);
            if (request == null) {
                return;
            }
            Util.closeQuietly(this.rawSocket);
            this.rawSocket = null;
            this.sink = null;
            this.source = null;
        } while (true);
    }

    private Request createTunnel(int n, int n2, Request object, HttpUrl object2) throws IOException {
        Object var9_5 = null;
        String string2 = "CONNECT " + Util.hostHeader((HttpUrl)object2, true) + " HTTP/1.1";
        block4: do {
            long l;
            object2 = new Http1Codec(null, null, this.source, this.sink);
            this.source.timeout().timeout(n, TimeUnit.MILLISECONDS);
            this.sink.timeout().timeout(n2, TimeUnit.MILLISECONDS);
            ((Http1Codec)object2).writeRequest(((Request)object).headers(), string2);
            ((Http1Codec)object2).finishRequest();
            Response response = ((Http1Codec)object2).readResponseHeaders(false).request((Request)object).build();
            long l2 = l = HttpHeaders.contentLength(response);
            if (l == -1L) {
                l2 = 0L;
            }
            object = ((Http1Codec)object2).newFixedLengthSource(l2);
            Util.skipAll((Source)object, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            object.close();
            switch (response.code()) {
                default: {
                    throw new IOException("Unexpected response code for CONNECT: " + response.code());
                }
                case 200: {
                    if (this.source.buffer().exhausted()) {
                        object2 = var9_5;
                        if (this.sink.buffer().exhausted()) break block4;
                    }
                    throw new IOException("TLS tunnel buffered too many bytes!");
                }
                case 407: {
                    object2 = this.route.address().proxyAuthenticator().authenticate(this.route, response);
                    if (object2 == null) {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                    object = object2;
                    if (!"close".equalsIgnoreCase(response.header("Connection"))) continue block4;
                }
            }
            break;
        } while (true);
        return object2;
    }

    private Request createTunnelRequest() {
        return new Request.Builder().url(this.route.address().url()).header("Host", Util.hostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void establishProtocol(ConnectionSpecSelector connectionSpecSelector) throws IOException {
        if (this.route.address().sslSocketFactory() == null) {
            this.protocol = Protocol.HTTP_1_1;
            this.socket = this.rawSocket;
            return;
        } else {
            this.connectTls(connectionSpecSelector);
            if (this.protocol != Protocol.HTTP_2) return;
            {
                this.socket.setSoTimeout(0);
                this.http2Connection = new Http2Connection.Builder(true).socket(this.socket, this.route.address().url().host(), this.source, this.sink).listener(this).build();
                this.http2Connection.start();
                return;
            }
        }
    }

    public void cancel() {
        Util.closeQuietly(this.rawSocket);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connect(int n, int n2, int n3, boolean bl) {
        if (this.protocol != null) {
            throw new IllegalStateException("already connected");
        }
        Object object = null;
        Object object2 = this.route.address().connectionSpecs();
        ConnectionSpecSelector connectionSpecSelector = new ConnectionSpecSelector((List<ConnectionSpec>)object2);
        Object object3 = object;
        if (this.route.address().sslSocketFactory() == null) {
            if (!object2.contains(ConnectionSpec.CLEARTEXT)) {
                throw new RouteException(new UnknownServiceException("CLEARTEXT communication not enabled for client"));
            }
            object2 = this.route.address().url().host();
            object3 = object;
            if (!Platform.get().isCleartextTrafficPermitted((String)object2)) {
                throw new RouteException(new UnknownServiceException("CLEARTEXT communication to " + (String)object2 + " not permitted by network security policy"));
            }
        }
        do {
            block16: {
                try {
                    if (this.route.requiresTunnel()) {
                        this.connectTunnel(n, n2, n3);
                    } else {
                        this.connectSocket(n, n2);
                    }
                    this.establishProtocol(connectionSpecSelector);
                    if (this.http2Connection == null) break block16;
                    object3 = this.connectionPool;
                }
                catch (IOException iOException) {
                    Util.closeQuietly(this.socket);
                    Util.closeQuietly(this.rawSocket);
                    this.socket = null;
                    this.rawSocket = null;
                    this.source = null;
                    this.sink = null;
                    this.handshake = null;
                    this.protocol = null;
                    this.http2Connection = null;
                    if (object3 == null) {
                        object = new RouteException(iOException);
                    } else {
                        ((RouteException)object3).addConnectException(iOException);
                        object = object3;
                    }
                    if (bl) {
                        object3 = object;
                        if (connectionSpecSelector.connectionFailed(iOException)) continue;
                    }
                    throw object;
                }
                synchronized (object3) {
                    this.allocationLimit = this.http2Connection.maxConcurrentStreams();
                }
            }
            return;
            break;
        } while (true);
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public boolean isEligible(Address address) {
        return this.allocations.size() < this.allocationLimit && address.equals(this.route().address()) && !this.noNewStreams;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isHealthy(boolean bl) {
        int n;
        block10: {
            boolean bl2 = true;
            if (this.socket.isClosed()) return false;
            if (this.socket.isInputShutdown()) return false;
            if (this.socket.isOutputShutdown()) {
                return false;
            }
            if (this.http2Connection != null) {
                if (!this.http2Connection.isShutdown()) return bl2;
                return false;
            }
            if (!bl) return bl2;
            n = this.socket.getSoTimeout();
            try {
                this.socket.setSoTimeout(1);
                bl = this.source.exhausted();
                if (!bl) break block10;
            }
            catch (Throwable throwable) {
                try {
                    this.socket.setSoTimeout(n);
                    throw throwable;
                }
                catch (IOException iOException) {
                    return false;
                }
                catch (SocketTimeoutException socketTimeoutException) {
                    return true;
                }
            }
            this.socket.setSoTimeout(n);
            return false;
        }
        this.socket.setSoTimeout(n);
        return true;
    }

    public boolean isMultiplexed() {
        return this.http2Connection != null;
    }

    public HttpCodec newCodec(OkHttpClient okHttpClient, StreamAllocation streamAllocation) throws SocketException {
        if (this.http2Connection != null) {
            return new Http2Codec(okHttpClient, streamAllocation, this.http2Connection);
        }
        this.socket.setSoTimeout(okHttpClient.readTimeoutMillis());
        this.source.timeout().timeout(okHttpClient.readTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.sink.timeout().timeout(okHttpClient.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        return new Http1Codec(okHttpClient, streamAllocation, this.source, this.sink);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSettings(Http2Connection http2Connection) {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            this.allocationLimit = http2Connection.maxConcurrentStreams();
            return;
        }
    }

    @Override
    public void onStream(Http2Stream http2Stream) throws IOException {
        http2Stream.close(ErrorCode.REFUSED_STREAM);
    }

    @Override
    public Protocol protocol() {
        return this.protocol;
    }

    @Override
    public Route route() {
        return this.route;
    }

    public Socket socket() {
        return this.socket;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("Connection{").append(this.route.address().url().host()).append(":").append(this.route.address().url().port()).append(", proxy=").append(this.route.proxy()).append(" hostAddress=").append(this.route.socketAddress()).append(" cipherSuite=");
        if (this.handshake != null) {
            object = this.handshake.cipherSuite();
            do {
                return stringBuilder.append(object).append(" protocol=").append((Object)this.protocol).append('}').toString();
                break;
            } while (true);
        }
        object = "none";
        return stringBuilder.append(object).append(" protocol=").append((Object)this.protocol).append('}').toString();
    }
}

