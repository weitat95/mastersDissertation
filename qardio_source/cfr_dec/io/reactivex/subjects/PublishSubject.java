/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.Subject;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class PublishSubject<T>
extends Subject<T> {
    static final PublishDisposable[] EMPTY;
    static final PublishDisposable[] TERMINATED;
    Throwable error;
    final AtomicReference<PublishDisposable<T>[]> subscribers = new AtomicReference<PublishDisposable[]>(EMPTY);

    static {
        TERMINATED = new PublishDisposable[0];
        EMPTY = new PublishDisposable[0];
    }

    PublishSubject() {
    }

    public static <T> PublishSubject<T> create() {
        return new PublishSubject<T>();
    }

    boolean add(PublishDisposable<T> publishDisposable) {
        PublishDisposable[] arrpublishDisposable;
        PublishDisposable<T>[] arrpublishDisposable2;
        do {
            if ((arrpublishDisposable2 = this.subscribers.get()) == TERMINATED) {
                return false;
            }
            int n = arrpublishDisposable2.length;
            arrpublishDisposable = new PublishDisposable[n + 1];
            System.arraycopy(arrpublishDisposable2, 0, arrpublishDisposable, 0, n);
            arrpublishDisposable[n] = publishDisposable;
        } while (!this.subscribers.compareAndSet(arrpublishDisposable2, arrpublishDisposable));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onComplete() {
        if (this.subscribers.get() != TERMINATED) {
            PublishDisposable[] arrpublishDisposable = this.subscribers.getAndSet(TERMINATED);
            int n = arrpublishDisposable.length;
            for (int i = 0; i < n; ++i) {
                arrpublishDisposable[i].onComplete();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onError(Throwable arrpublishDisposable) {
        if (this.subscribers.get() == TERMINATED) {
            RxJavaPlugins.onError((Throwable)arrpublishDisposable);
            return;
        } else {
            Object object = arrpublishDisposable;
            if (arrpublishDisposable == null) {
                object = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.error = object;
            arrpublishDisposable = this.subscribers.getAndSet(TERMINATED);
            int n = arrpublishDisposable.length;
            for (int i = 0; i < n; ++i) {
                arrpublishDisposable[i].onError((Throwable)object);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNext(T t) {
        if (this.subscribers.get() != TERMINATED) {
            if (t == null) {
                this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            PublishDisposable<T>[] arrpublishDisposable = this.subscribers.get();
            int n = arrpublishDisposable.length;
            for (int i = 0; i < n; ++i) {
                arrpublishDisposable[i].onNext(t);
            }
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (this.subscribers.get() == TERMINATED) {
            disposable.dispose();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void remove(PublishDisposable<T> publishDisposable) {
        PublishDisposable<T>[] arrpublishDisposable;
        block0 : while ((arrpublishDisposable = this.subscribers.get()) != TERMINATED && arrpublishDisposable != EMPTY) {
            int n = arrpublishDisposable.length;
            int n2 = -1;
            int n3 = 0;
            do {
                block8: {
                    int n4;
                    PublishDisposable[] arrpublishDisposable2;
                    block7: {
                        n4 = n2;
                        if (n3 >= n) break block7;
                        if (arrpublishDisposable[n3] != publishDisposable) break block8;
                        n4 = n3;
                    }
                    if (n4 < 0) break block0;
                    if (n == 1) {
                        arrpublishDisposable2 = EMPTY;
                    } else {
                        arrpublishDisposable2 = new PublishDisposable[n - 1];
                        System.arraycopy(arrpublishDisposable, 0, arrpublishDisposable2, 0, n4);
                        System.arraycopy(arrpublishDisposable, n4 + 1, arrpublishDisposable2, n4, n - n4 - 1);
                    }
                    if (!this.subscribers.compareAndSet(arrpublishDisposable, arrpublishDisposable2)) continue block0;
                    return;
                }
                ++n3;
            } while (true);
        }
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        Serializable serializable = new PublishDisposable<T>(observer, this);
        observer.onSubscribe((Disposable)((Object)serializable));
        if (this.add((PublishDisposable<T>)serializable)) {
            if (serializable.isDisposed()) {
                this.remove((PublishDisposable<T>)serializable);
            }
            return;
        }
        serializable = this.error;
        if (serializable != null) {
            observer.onError((Throwable)serializable);
            return;
        }
        observer.onComplete();
    }

    static final class PublishDisposable<T>
    extends AtomicBoolean
    implements Disposable {
        final Observer<? super T> actual;
        final PublishSubject<T> parent;

        PublishDisposable(Observer<? super T> observer, PublishSubject<T> publishSubject) {
            this.actual = observer;
            this.parent = publishSubject;
        }

        @Override
        public void dispose() {
            if (this.compareAndSet(false, true)) {
                this.parent.remove(this);
            }
        }

        @Override
        public boolean isDisposed() {
            return this.get();
        }

        public void onComplete() {
            if (!this.get()) {
                this.actual.onComplete();
            }
        }

        public void onError(Throwable throwable) {
            if (this.get()) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.actual.onError(throwable);
        }

        public void onNext(T t) {
            if (!this.get()) {
                this.actual.onNext(t);
            }
        }
    }

}

