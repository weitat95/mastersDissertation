/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

public final class StreamAllocation {
    static final /* synthetic */ boolean $assertionsDisabled;
    public final Address address;
    private final Object callStackTrace;
    private boolean canceled;
    private HttpCodec codec;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private int refusedStreamCount;
    private boolean released;
    private Route route;
    private final RouteSelector routeSelector;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !StreamAllocation.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public StreamAllocation(ConnectionPool connectionPool, Address address, Object object) {
        this.connectionPool = connectionPool;
        this.address = address;
        this.routeSelector = new RouteSelector(address, this.routeDatabase());
        this.callStackTrace = object;
    }

    private Socket deallocate(boolean bl, boolean bl2, boolean bl3) {
        Socket socket;
        block9: {
            Socket socket2;
            block10: {
                if (!$assertionsDisabled && !Thread.holdsLock(this.connectionPool)) {
                    throw new AssertionError();
                }
                if (bl3) {
                    this.codec = null;
                }
                if (bl2) {
                    this.released = true;
                }
                Socket socket3 = null;
                socket2 = null;
                socket = socket3;
                if (this.connection == null) break block9;
                if (bl) {
                    this.connection.noNewStreams = true;
                }
                socket = socket3;
                if (this.codec != null) break block9;
                if (this.released) break block10;
                socket = socket3;
                if (!this.connection.noNewStreams) break block9;
            }
            this.release(this.connection);
            socket = socket2;
            if (this.connection.allocations.isEmpty()) {
                this.connection.idleAtNanos = System.nanoTime();
                socket = socket2;
                if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                    socket = this.connection.socket();
                }
            }
            this.connection = null;
        }
        return socket;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RealConnection findConnection(int n, int n2, int n3, boolean bl) throws IOException {
        Object object;
        RealConnection realConnection;
        Object object2 = this.connectionPool;
        synchronized (object2) {
            if (this.released) {
                throw new IllegalStateException("released");
            }
            if (this.codec != null) {
                throw new IllegalStateException("codec != null");
            }
            if (this.canceled) {
                throw new IOException("Canceled");
            }
            object = this.connection;
            if (object != null && !((RealConnection)object).noNewStreams) {
                return object;
            }
            Internal.instance.get(this.connectionPool, this.address, this);
            if (this.connection != null) {
                return this.connection;
            }
            object = this.route;
        }
        object2 = object;
        if (object == null) {
            object2 = this.routeSelector.next();
        }
        object = this.connectionPool;
        synchronized (object) {
            this.route = object2;
            this.refusedStreamCount = 0;
            realConnection = new RealConnection(this.connectionPool, (Route)object2);
            this.acquire(realConnection);
            if (this.canceled) {
                throw new IOException("Canceled");
            }
        }
        realConnection.connect(n, n2, n3, bl);
        this.routeDatabase().connected(realConnection.route());
        object = null;
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            Internal.instance.put(this.connectionPool, realConnection);
            object2 = realConnection;
            if (realConnection.isMultiplexed()) {
                object = Internal.instance.deduplicate(this.connectionPool, this.address, this);
                object2 = this.connection;
            }
        }
        Util.closeQuietly((Socket)object);
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private RealConnection findHealthyConnection(int n, int n2, int n3, boolean bl, boolean bl2) throws IOException {
        do {
            RealConnection realConnection = this.findConnection(n, n2, n3, bl);
            ConnectionPool connectionPool = this.connectionPool;
            // MONITORENTER : connectionPool
            if (realConnection.successCount == 0) {
                // MONITOREXIT : connectionPool
                return realConnection;
            }
            // MONITOREXIT : connectionPool
            if (realConnection.isHealthy(bl2)) return realConnection;
            this.noNewStreams();
        } while (true);
    }

    private void release(RealConnection realConnection) {
        int n = realConnection.allocations.size();
        for (int i = 0; i < n; ++i) {
            if (realConnection.allocations.get(i).get() != this) continue;
            realConnection.allocations.remove(i);
            return;
        }
        throw new IllegalStateException();
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public void acquire(RealConnection realConnection) {
        if (!$assertionsDisabled && !Thread.holdsLock(this.connectionPool)) {
            throw new AssertionError();
        }
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        this.connection = realConnection;
        realConnection.allocations.add(new StreamAllocationReference(this, this.callStackTrace));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void cancel() {
        ConnectionPool connectionPool = this.connectionPool;
        // MONITORENTER : connectionPool
        this.canceled = true;
        HttpCodec httpCodec = this.codec;
        RealConnection realConnection = this.connection;
        // MONITOREXIT : connectionPool
        if (httpCodec != null) {
            httpCodec.cancel();
            return;
        }
        if (realConnection == null) return;
        realConnection.cancel();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HttpCodec codec() {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            return this.codec;
        }
    }

    public RealConnection connection() {
        synchronized (this) {
            RealConnection realConnection = this.connection;
            return realConnection;
        }
    }

    public boolean hasMoreRoutes() {
        return this.route != null || this.routeSelector.hasNext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HttpCodec newStream(OkHttpClient object, boolean bl) {
        HttpCodec httpCodec;
        int n = ((OkHttpClient)object).connectTimeoutMillis();
        int n2 = ((OkHttpClient)object).readTimeoutMillis();
        int n3 = ((OkHttpClient)object).writeTimeoutMillis();
        boolean bl2 = ((OkHttpClient)object).retryOnConnectionFailure();
        try {
            httpCodec = this.findHealthyConnection(n, n2, n3, bl2, bl).newCodec((OkHttpClient)object, this);
            object = this.connectionPool;
            synchronized (object) {
                this.codec = httpCodec;
            }
        }
        catch (IOException iOException) {
            throw new RouteException(iOException);
        }
        return httpCodec;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void noNewStreams() {
        Socket socket;
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            socket = this.deallocate(true, false, false);
        }
        Util.closeQuietly(socket);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        Socket socket;
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            socket = this.deallocate(false, true, false);
        }
        Util.closeQuietly(socket);
    }

    public Socket releaseAndAcquire(RealConnection realConnection) {
        if (!$assertionsDisabled && !Thread.holdsLock(this.connectionPool)) {
            throw new AssertionError();
        }
        if (this.codec != null || this.connection.allocations.size() != 1) {
            throw new IllegalStateException();
        }
        Reference<StreamAllocation> reference = this.connection.allocations.get(0);
        Socket socket = this.deallocate(true, false, false);
        this.connection = realConnection;
        realConnection.allocations.add(reference);
        return socket;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void streamFailed(IOException object) {
        boolean bl = false;
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            boolean bl2;
            block14: {
                block15: {
                    block12: {
                        block13: {
                            if (!(object instanceof StreamResetException)) break block12;
                            object = (StreamResetException)object;
                            if (((StreamResetException)object).errorCode == ErrorCode.REFUSED_STREAM) {
                                ++this.refusedStreamCount;
                            }
                            if (((StreamResetException)object).errorCode != ErrorCode.REFUSED_STREAM) break block13;
                            bl2 = bl;
                            if (this.refusedStreamCount <= 1) break block14;
                        }
                        bl2 = true;
                        this.route = null;
                        break block14;
                    }
                    bl2 = bl;
                    if (this.connection == null) break block14;
                    if (!this.connection.isMultiplexed()) break block15;
                    bl2 = bl;
                    if (!(object instanceof ConnectionShutdownException)) break block14;
                }
                bl2 = bl = true;
                if (this.connection.successCount == 0) {
                    if (this.route != null && object != null) {
                        this.routeSelector.connectFailed(this.route, (IOException)object);
                    }
                    this.route = null;
                    bl2 = bl;
                }
            }
            object = this.deallocate(bl2, false, true);
        }
        Util.closeQuietly((Socket)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void streamFinished(boolean bl, HttpCodec object) {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            if (object == null || object != this.codec) {
                throw new IllegalStateException("expected " + this.codec + " but was " + object);
            }
            if (!bl) {
                object = this.connection;
                ++((RealConnection)object).successCount;
            }
            object = this.deallocate(bl, false, true);
        }
        Util.closeQuietly((Socket)object);
    }

    public String toString() {
        RealConnection realConnection = this.connection();
        if (realConnection != null) {
            return realConnection.toString();
        }
        return this.address.toString();
    }

    public static final class StreamAllocationReference
    extends WeakReference<StreamAllocation> {
        public final Object callStackTrace;

        StreamAllocationReference(StreamAllocation streamAllocation, Object object) {
            super(streamAllocation);
            this.callStackTrace = object;
        }
    }

}

