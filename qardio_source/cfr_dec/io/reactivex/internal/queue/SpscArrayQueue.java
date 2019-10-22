/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscArrayQueue<E>
extends AtomicReferenceArray<E>
implements SimplePlainQueue<E> {
    private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    final AtomicLong consumerIndex;
    final int lookAheadStep;
    final int mask = this.length() - 1;
    final AtomicLong producerIndex = new AtomicLong();
    long producerLookAhead;

    public SpscArrayQueue(int n) {
        super(Pow2.roundToPowerOfTwo(n));
        this.consumerIndex = new AtomicLong();
        this.lookAheadStep = Math.min(n / 4, MAX_LOOK_AHEAD_STEP);
    }

    int calcElementOffset(long l) {
        return (int)l & this.mask;
    }

    int calcElementOffset(long l, int n) {
        return (int)l & n;
    }

    @Override
    public void clear() {
        while (this.poll() != null || !this.isEmpty()) {
        }
    }

    @Override
    public boolean isEmpty() {
        return this.producerIndex.get() == this.consumerIndex.get();
    }

    E lvElement(int n) {
        return this.get(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        int n = this.mask;
        long l = this.producerIndex.get();
        int n2 = this.calcElementOffset(l, n);
        if (l >= this.producerLookAhead) {
            int n3 = this.lookAheadStep;
            if (this.lvElement(this.calcElementOffset((long)n3 + l, n)) == null) {
                this.producerLookAhead = (long)n3 + l;
            } else if (this.lvElement(n2) != null) {
                return false;
            }
        }
        this.soElement(n2, e);
        this.soProducerIndex(1L + l);
        return true;
    }

    @Override
    public E poll() {
        long l = this.consumerIndex.get();
        int n = this.calcElementOffset(l);
        E e = this.lvElement(n);
        if (e == null) {
            return null;
        }
        this.soConsumerIndex(1L + l);
        this.soElement(n, null);
        return e;
    }

    void soConsumerIndex(long l) {
        this.consumerIndex.lazySet(l);
    }

    void soElement(int n, E e) {
        this.lazySet(n, e);
    }

    void soProducerIndex(long l) {
        this.producerIndex.lazySet(l);
    }
}

