/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.RealCall;
import okhttp3.internal.Util;

public final class Dispatcher {
    private ExecutorService executorService;
    private Runnable idleCallback;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();
    private final Deque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<RealCall>();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private <T> void finished(Deque<T> object, T t, boolean bl) {
        // MONITORENTER : this
        if (!object.remove(t)) {
            throw new AssertionError((Object)"Call wasn't in-flight!");
        }
        if (bl) {
            this.promoteCalls();
        }
        int n = this.runningCallsCount();
        object = this.idleCallback;
        // MONITOREXIT : this
        if (n != 0) return;
        if (object == null) return;
        object.run();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void promoteCalls() {
        if (this.runningAsyncCalls.size() >= this.maxRequests) {
            return;
        }
        if (this.readyAsyncCalls.isEmpty()) return;
        Iterator<RealCall.AsyncCall> iterator = this.readyAsyncCalls.iterator();
        do {
            if (!iterator.hasNext()) return;
            RealCall.AsyncCall asyncCall = iterator.next();
            if (this.runningCallsForHost(asyncCall) >= this.maxRequestsPerHost) continue;
            iterator.remove();
            this.runningAsyncCalls.add(asyncCall);
            this.executorService().execute(asyncCall);
        } while (this.runningAsyncCalls.size() < this.maxRequests);
    }

    private int runningCallsForHost(RealCall.AsyncCall asyncCall) {
        int n = 0;
        Iterator<RealCall.AsyncCall> iterator = this.runningAsyncCalls.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().host().equals(asyncCall.host())) continue;
            ++n;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void enqueue(RealCall.AsyncCall asyncCall) {
        synchronized (this) {
            if (this.runningAsyncCalls.size() < this.maxRequests && this.runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                this.runningAsyncCalls.add(asyncCall);
                this.executorService().execute(asyncCall);
            } else {
                this.readyAsyncCalls.add(asyncCall);
            }
            return;
        }
    }

    void executed(RealCall realCall) {
        synchronized (this) {
            this.runningSyncCalls.add(realCall);
            return;
        }
    }

    public ExecutorService executorService() {
        synchronized (this) {
            if (this.executorService == null) {
                this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
            }
            ExecutorService executorService = this.executorService;
            return executorService;
        }
    }

    void finished(RealCall.AsyncCall asyncCall) {
        this.finished(this.runningAsyncCalls, asyncCall, true);
    }

    void finished(RealCall realCall) {
        this.finished(this.runningSyncCalls, realCall, false);
    }

    public int runningCallsCount() {
        synchronized (this) {
            int n = this.runningAsyncCalls.size();
            int n2 = this.runningSyncCalls.size();
            return n + n2;
        }
    }
}

