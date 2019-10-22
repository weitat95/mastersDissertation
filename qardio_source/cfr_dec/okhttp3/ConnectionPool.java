/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.lang.ref.Reference;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Address;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;

public final class ConnectionPool {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Executor executor;
    private final Runnable cleanupRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            long l;
            while ((l = ConnectionPool.this.cleanup(System.nanoTime())) != -1L) {
                if (l <= 0L) continue;
                long l2 = l / 1000000L;
                ConnectionPool connectionPool = ConnectionPool.this;
                synchronized (connectionPool) {
                    try {
                        ConnectionPool.this.wait(l2, (int)(l - l2 * 1000000L));
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
            return;
        }
    };
    boolean cleanupRunning;
    private final Deque<RealConnection> connections = new ArrayDeque<RealConnection>();
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;
    final RouteDatabase routeDatabase = new RouteDatabase();

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ConnectionPool.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
    }

    public ConnectionPool() {
        this(5, 5L, TimeUnit.MINUTES);
    }

    public ConnectionPool(int n, long l, TimeUnit timeUnit) {
        this.maxIdleConnections = n;
        this.keepAliveDurationNs = timeUnit.toNanos(l);
        if (l <= 0L) {
            throw new IllegalArgumentException("keepAliveDuration <= 0: " + l);
        }
    }

    private int pruneAndGetAllocationCount(RealConnection realConnection, long l) {
        List<Reference<StreamAllocation>> list = realConnection.allocations;
        int n = 0;
        while (n < list.size()) {
            StreamAllocation.StreamAllocationReference streamAllocationReference = list.get(n);
            if (streamAllocationReference.get() != null) {
                ++n;
                continue;
            }
            streamAllocationReference = streamAllocationReference;
            String string2 = "A connection to " + realConnection.route().address().url() + " was leaked. Did you forget to close a response body?";
            Platform.get().logCloseableLeak(string2, streamAllocationReference.callStackTrace);
            list.remove(n);
            realConnection.noNewStreams = true;
            if (!list.isEmpty()) continue;
            realConnection.idleAtNanos = l - this.keepAliveDurationNs;
            return 0;
        }
        return list.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    long cleanup(long l) {
        int n = 0;
        int n2 = 0;
        RealConnection realConnection = null;
        long l2 = Long.MIN_VALUE;
        synchronized (this) {
            for (RealConnection realConnection2 : this.connections) {
                if (this.pruneAndGetAllocationCount(realConnection2, l) > 0) {
                    ++n;
                    continue;
                }
                int n3 = n2 + 1;
                long l3 = l - realConnection2.idleAtNanos;
                n2 = n3;
                if (l3 <= l2) continue;
                l2 = l3;
                realConnection = realConnection2;
                n2 = n3;
            }
            if (l2 >= this.keepAliveDurationNs || n2 > this.maxIdleConnections) {
                this.connections.remove(realConnection);
                // MONITOREXIT [4, 8] lbl23 : MonitorExitStatement: MONITOREXIT : this
                Util.closeQuietly(realConnection.socket());
                return 0L;
            }
            if (n2 > 0) {
                l = this.keepAliveDurationNs;
                return l - l2;
            }
            if (n > 0) {
                return this.keepAliveDurationNs;
            }
            this.cleanupRunning = false;
            return -1L;
        }
    }

    boolean connectionBecameIdle(RealConnection realConnection) {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        if (realConnection.noNewStreams || this.maxIdleConnections == 0) {
            this.connections.remove(realConnection);
            return true;
        }
        this.notifyAll();
        return false;
    }

    Socket deduplicate(Address address, StreamAllocation streamAllocation) {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        for (RealConnection realConnection : this.connections) {
            if (!realConnection.isEligible(address) || !realConnection.isMultiplexed() || realConnection == streamAllocation.connection()) continue;
            return streamAllocation.releaseAndAcquire(realConnection);
        }
        return null;
    }

    RealConnection get(Address address, StreamAllocation streamAllocation) {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        for (RealConnection realConnection : this.connections) {
            if (!realConnection.isEligible(address)) continue;
            streamAllocation.acquire(realConnection);
            return realConnection;
        }
        return null;
    }

    void put(RealConnection realConnection) {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        if (!this.cleanupRunning) {
            this.cleanupRunning = true;
            executor.execute(this.cleanupRunnable);
        }
        this.connections.add(realConnection);
    }

}

