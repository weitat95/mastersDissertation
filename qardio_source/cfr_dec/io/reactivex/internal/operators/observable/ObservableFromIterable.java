/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

public final class ObservableFromIterable<T>
extends Observable<T> {
    final Iterable<? extends T> source;

    public ObservableFromIterable(Iterable<? extends T> iterable) {
        this.source = iterable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void subscribeActual(Observer<? super T> observer) {
        Object object;
        block5: {
            try {
                object = this.source.iterator();
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptyDisposable.error(throwable, observer);
                return;
            }
            try {
                boolean bl = object.hasNext();
                if (bl) break block5;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptyDisposable.error(throwable, observer);
                return;
            }
            EmptyDisposable.complete(observer);
            return;
        }
        object = new FromIterableDisposable<T>((Observer<? extends T>)observer, (Iterator<? extends T>)object);
        observer.onSubscribe((Disposable)object);
        if (((FromIterableDisposable)object).fusionMode) return;
        {
            ((FromIterableDisposable)object).run();
            return;
        }
    }

    static final class FromIterableDisposable<T>
    extends BasicQueueDisposable<T> {
        final Observer<? super T> actual;
        boolean checkNext;
        volatile boolean disposed;
        boolean done;
        boolean fusionMode;
        final Iterator<? extends T> it;

        FromIterableDisposable(Observer<? super T> observer, Iterator<? extends T> iterator) {
            this.actual = observer;
            this.it = iterator;
        }

        @Override
        public void clear() {
            this.done = true;
        }

        @Override
        public void dispose() {
            this.disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override
        public boolean isEmpty() {
            return this.done;
        }

        @Override
        public T poll() {
            if (this.done) {
                return null;
            }
            if (this.checkNext) {
                if (!this.it.hasNext()) {
                    this.done = true;
                    return null;
                }
            } else {
                this.checkNext = true;
            }
            return ObjectHelper.requireNonNull(this.it.next(), "The iterator returned a null value");
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 1) != 0) {
                this.fusionMode = true;
                return 1;
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void run() {
            block5: {
                while (!this.isDisposed()) {
                    try {
                        T t = ObjectHelper.requireNonNull(this.it.next(), "The iterator returned a null value");
                        this.actual.onNext(t);
                        if (this.isDisposed()) break;
                    }
                    catch (Throwable throwable) {
                        Exceptions.throwIfFatal(throwable);
                        this.actual.onError(throwable);
                        return;
                    }
                    try {}
                    catch (Throwable throwable) {
                        Exceptions.throwIfFatal(throwable);
                        this.actual.onError(throwable);
                        return;
                    }
                    boolean bl = this.it.hasNext();
                    if (bl) continue;
                    if (!this.isDisposed()) break block5;
                }
                return;
            }
            this.actual.onComplete();
        }
    }

}

