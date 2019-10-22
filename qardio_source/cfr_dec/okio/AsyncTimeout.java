/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.Segment;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio.Util;

public class AsyncTimeout
extends Timeout {
    private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
    private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
    private static AsyncTimeout head;
    private boolean inQueue;
    private AsyncTimeout next;
    private long timeoutAt;

    static AsyncTimeout awaitTimeout() throws InterruptedException {
        Object var5 = null;
        AsyncTimeout asyncTimeout = AsyncTimeout.head.next;
        if (asyncTimeout == null) {
            long l = System.nanoTime();
            AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
            asyncTimeout = var5;
            if (AsyncTimeout.head.next == null) {
                asyncTimeout = var5;
                if (System.nanoTime() - l >= IDLE_TIMEOUT_NANOS) {
                    asyncTimeout = head;
                }
            }
            return asyncTimeout;
        }
        long l = asyncTimeout.remainingNanos(System.nanoTime());
        if (l > 0L) {
            long l2 = l / 1000000L;
            AsyncTimeout.class.wait(l2, (int)(l - l2 * 1000000L));
            return null;
        }
        AsyncTimeout.head.next = asyncTimeout.next;
        asyncTimeout.next = null;
        return asyncTimeout;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
        synchronized (AsyncTimeout.class) {
            AsyncTimeout asyncTimeout2 = head;
            while (asyncTimeout2 != null) {
                block6: {
                    if (asyncTimeout2.next != asyncTimeout) break block6;
                    asyncTimeout2.next = asyncTimeout.next;
                    asyncTimeout.next = null;
                    return false;
                }
                asyncTimeout2 = asyncTimeout2.next;
            }
            return true;
        }
    }

    private long remainingNanos(long l) {
        return this.timeoutAt - l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void scheduleTimeout(AsyncTimeout asyncTimeout, long l, boolean bl) {
        synchronized (AsyncTimeout.class) {
            if (head == null) {
                head = new AsyncTimeout();
                new Watchdog().start();
            }
            long l2 = System.nanoTime();
            if (l != 0L && bl) {
                asyncTimeout.timeoutAt = Math.min(l, asyncTimeout.deadlineNanoTime() - l2) + l2;
            } else if (l != 0L) {
                asyncTimeout.timeoutAt = l2 + l;
            } else {
                if (!bl) {
                    throw new AssertionError();
                }
                asyncTimeout.timeoutAt = asyncTimeout.deadlineNanoTime();
            }
            l = asyncTimeout.remainingNanos(l2);
            AsyncTimeout asyncTimeout2 = head;
            do {
                if (asyncTimeout2.next == null || l < asyncTimeout2.next.remainingNanos(l2)) {
                    asyncTimeout.next = asyncTimeout2.next;
                    asyncTimeout2.next = asyncTimeout;
                    if (asyncTimeout2 == head) {
                        AsyncTimeout.class.notify();
                    }
                    return;
                }
                asyncTimeout2 = asyncTimeout2.next;
            } while (true);
        }
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long l = this.timeoutNanos();
        boolean bl = this.hasDeadline();
        if (l == 0L && !bl) {
            return;
        }
        this.inQueue = true;
        AsyncTimeout.scheduleTimeout(this, l, bl);
    }

    final IOException exit(IOException iOException) throws IOException {
        if (!this.exit()) {
            return iOException;
        }
        return this.newTimeoutException(iOException);
    }

    final void exit(boolean bl) throws IOException {
        if (this.exit() && bl) {
            throw this.newTimeoutException(null);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return AsyncTimeout.cancelScheduledTimeout(this);
    }

    protected IOException newTimeoutException(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final Sink sink(final Sink sink) {
        return new Sink(){

            @Override
            public void close() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    sink.close();
                }
                catch (IOException iOException) {
                    try {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                    catch (Throwable throwable) {
                        AsyncTimeout.this.exit(false);
                        throw throwable;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public void flush() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    sink.flush();
                }
                catch (IOException iOException) {
                    try {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                    catch (Throwable throwable) {
                        AsyncTimeout.this.exit(false);
                        throw throwable;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.sink(" + sink + ")";
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void write(Buffer buffer, long l) throws IOException {
                Util.checkOffsetAndCount(buffer.size, 0L, l);
                block4 : while (l > 0L) {
                    long l2 = 0L;
                    Segment segment = buffer.head;
                    do {
                        block9: {
                            long l3;
                            block8: {
                                l3 = l2;
                                if (l2 >= 65536L) break block8;
                                if ((l2 += (long)(buffer.head.limit - buffer.head.pos)) < l) break block9;
                                l3 = l;
                            }
                            AsyncTimeout.this.enter();
                            try {
                                sink.write(buffer, l3);
                                l -= l3;
                            }
                            catch (IOException iOException) {
                                try {
                                    throw AsyncTimeout.this.exit(iOException);
                                }
                                catch (Throwable throwable) {
                                    AsyncTimeout.this.exit(false);
                                    throw throwable;
                                }
                            }
                            AsyncTimeout.this.exit(true);
                            continue block4;
                        }
                        segment = segment.next;
                    } while (true);
                    break;
                }
                return;
            }
        };
    }

    public final Source source(final Source source) {
        return new Source(){

            @Override
            public void close() throws IOException {
                try {
                    source.close();
                }
                catch (IOException iOException) {
                    try {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                    catch (Throwable throwable) {
                        AsyncTimeout.this.exit(false);
                        throw throwable;
                    }
                }
                AsyncTimeout.this.exit(true);
                return;
            }

            @Override
            public long read(Buffer buffer, long l) throws IOException {
                AsyncTimeout.this.enter();
                try {
                    l = source.read(buffer, l);
                }
                catch (IOException iOException) {
                    try {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                    catch (Throwable throwable) {
                        AsyncTimeout.this.exit(false);
                        throw throwable;
                    }
                }
                AsyncTimeout.this.exit(true);
                return l;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.source(" + source + ")";
            }
        };
    }

    protected void timedOut() {
    }

    private static final class Watchdog
    extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            this.setDaemon(true);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            do {
                AsyncTimeout asyncTimeout;
                try {
                    synchronized (AsyncTimeout.class) {
                    }
                }
                catch (InterruptedException interruptedException) {
                    continue;
                }
                {
                    asyncTimeout = AsyncTimeout.awaitTimeout();
                    if (asyncTimeout == null) {
                        continue;
                    }
                    if (asyncTimeout == head) {
                        head = null;
                        return;
                    }
                }
                {
                    asyncTimeout.timedOut();
                    continue;
                }
                break;
            } while (true);
        }
    }

}

