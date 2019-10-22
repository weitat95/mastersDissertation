/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.rxrelay2;

import com.jakewharton.rxrelay2.AppendOnlyLinkedArrayList;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class BehaviorRelay<T>
extends Relay<T> {
    private static final BehaviorDisposable[] EMPTY;
    private static final Object[] EMPTY_ARRAY;
    long index;
    final Lock readLock;
    private final AtomicReference<BehaviorDisposable<T>[]> subscribers;
    final AtomicReference<T> value;
    private final Lock writeLock;

    static {
        EMPTY_ARRAY = new Object[0];
        EMPTY = new BehaviorDisposable[0];
    }

    private BehaviorRelay() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.readLock = reentrantReadWriteLock.readLock();
        this.writeLock = reentrantReadWriteLock.writeLock();
        this.subscribers = new AtomicReference<BehaviorDisposable[]>(EMPTY);
        this.value = new AtomicReference();
    }

    private void add(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable[] arrbehaviorDisposable;
        BehaviorDisposable<T>[] arrbehaviorDisposable2;
        do {
            arrbehaviorDisposable2 = this.subscribers.get();
            int n = arrbehaviorDisposable2.length;
            arrbehaviorDisposable = new BehaviorDisposable[n + 1];
            System.arraycopy(arrbehaviorDisposable2, 0, arrbehaviorDisposable, 0, n);
            arrbehaviorDisposable[n] = behaviorDisposable;
        } while (!this.subscribers.compareAndSet(arrbehaviorDisposable2, arrbehaviorDisposable));
    }

    public static <T> BehaviorRelay<T> create() {
        return new BehaviorRelay<T>();
    }

    private void setCurrent(T t) {
        this.writeLock.lock();
        try {
            ++this.index;
            this.value.lazySet(t);
            return;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public void accept(T t) {
        if (t == null) {
            throw new NullPointerException("value == null");
        }
        this.setCurrent(t);
        BehaviorDisposable<T>[] arrbehaviorDisposable = this.subscribers.get();
        int n = arrbehaviorDisposable.length;
        for (int i = 0; i < n; ++i) {
            arrbehaviorDisposable[i].emitNext(t, this.index);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void remove(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable<T>[] arrbehaviorDisposable;
        block0 : while ((arrbehaviorDisposable = this.subscribers.get()) != EMPTY) {
            int n = arrbehaviorDisposable.length;
            int n2 = -1;
            int n3 = 0;
            do {
                block8: {
                    int n4;
                    BehaviorDisposable[] arrbehaviorDisposable2;
                    block7: {
                        n4 = n2;
                        if (n3 >= n) break block7;
                        if (arrbehaviorDisposable[n3] != behaviorDisposable) break block8;
                        n4 = n3;
                    }
                    if (n4 < 0) break block0;
                    if (n == 1) {
                        arrbehaviorDisposable2 = EMPTY;
                    } else {
                        arrbehaviorDisposable2 = new BehaviorDisposable[n - 1];
                        System.arraycopy(arrbehaviorDisposable, 0, arrbehaviorDisposable2, 0, n4);
                        System.arraycopy(arrbehaviorDisposable, n4 + 1, arrbehaviorDisposable2, n4, n - n4 - 1);
                    }
                    if (!this.subscribers.compareAndSet(arrbehaviorDisposable, arrbehaviorDisposable2)) continue block0;
                    return;
                }
                ++n3;
            } while (true);
        }
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        BehaviorDisposable<T> behaviorDisposable = new BehaviorDisposable<T>(observer, this);
        observer.onSubscribe(behaviorDisposable);
        this.add(behaviorDisposable);
        if (behaviorDisposable.cancelled) {
            this.remove(behaviorDisposable);
            return;
        }
        behaviorDisposable.emitFirst();
    }

    static final class BehaviorDisposable<T>
    implements AppendOnlyLinkedArrayList.NonThrowingPredicate<T>,
    Disposable {
        final Observer<? super T> actual;
        volatile boolean cancelled;
        boolean emitting;
        boolean fastPath;
        long index;
        boolean next;
        AppendOnlyLinkedArrayList<T> queue;
        final BehaviorRelay<T> state;

        BehaviorDisposable(Observer<? super T> observer, BehaviorRelay<T> behaviorRelay) {
            this.actual = observer;
            this.state = behaviorRelay;
        }

        @Override
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.state.remove(this);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void emitFirst() {
            BehaviorRelay<T> behaviorRelay;
            block10: {
                block9: {
                    boolean bl = true;
                    if (this.cancelled) break block9;
                    synchronized (this) {
                        if (this.cancelled) {
                            return;
                        }
                        if (this.next) {
                            return;
                        }
                        behaviorRelay = this.state;
                        Lock lock = behaviorRelay.readLock;
                        lock.lock();
                        this.index = behaviorRelay.index;
                        behaviorRelay = behaviorRelay.value.get();
                        lock.unlock();
                        if (behaviorRelay == null) {
                            bl = false;
                        }
                        this.emitting = bl;
                        this.next = true;
                        if (behaviorRelay != null) break block10;
                    }
                }
                return;
            }
            this.test(behaviorRelay);
            this.emitLoop();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void emitLoop() {
            while (!this.cancelled) {
                AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList;
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        this.emitting = false;
                        return;
                    }
                    this.queue = null;
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void emitNext(T t, long l) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.index == l) {
                        return;
                    }
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList;
                        AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList2 = appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
                            this.queue = appendOnlyLinkedArrayList2;
                        }
                        appendOnlyLinkedArrayList2.add(t);
                        return;
                    }
                    this.next = true;
                }
                this.fastPath = true;
            }
            this.test(t);
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override
        public boolean test(T t) {
            if (!this.cancelled) {
                this.actual.onNext(t);
            }
            return false;
        }
    }

}

