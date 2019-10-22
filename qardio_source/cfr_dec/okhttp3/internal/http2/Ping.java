/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.util.concurrent.CountDownLatch;

final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long received = -1L;
    private long sent = -1L;

    Ping() {
    }

    void cancel() {
        if (this.received != -1L || this.sent == -1L) {
            throw new IllegalStateException();
        }
        this.received = this.sent - 1L;
        this.latch.countDown();
    }

    void receive() {
        if (this.received != -1L || this.sent == -1L) {
            throw new IllegalStateException();
        }
        this.received = System.nanoTime();
        this.latch.countDown();
    }

    void send() {
        if (this.sent != -1L) {
            throw new IllegalStateException();
        }
        this.sent = System.nanoTime();
    }
}

