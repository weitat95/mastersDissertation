/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout {
    public static final Timeout NONE = new Timeout(){

        @Override
        public Timeout deadlineNanoTime(long l) {
            return this;
        }

        @Override
        public void throwIfReached() throws IOException {
        }

        @Override
        public Timeout timeout(long l, TimeUnit timeUnit) {
            return this;
        }
    };
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public Timeout clearTimeout() {
        this.timeoutNanos = 0L;
        return this;
    }

    public long deadlineNanoTime() {
        if (!this.hasDeadline) {
            throw new IllegalStateException("No deadline");
        }
        return this.deadlineNanoTime;
    }

    public Timeout deadlineNanoTime(long l) {
        this.hasDeadline = true;
        this.deadlineNanoTime = l;
        return this;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("thread interrupted");
        }
        if (this.hasDeadline && this.deadlineNanoTime - System.nanoTime() <= 0L) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    public Timeout timeout(long l, TimeUnit timeUnit) {
        if (l < 0L) {
            throw new IllegalArgumentException("timeout < 0: " + l);
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        }
        this.timeoutNanos = timeUnit.toNanos(l);
        return this;
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

}

