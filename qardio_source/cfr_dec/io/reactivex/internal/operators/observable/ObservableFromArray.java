/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class ObservableFromArray<T>
extends Observable<T> {
    final T[] array;

    public ObservableFromArray(T[] arrT) {
        this.array = arrT;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        FromArrayDisposable<T> fromArrayDisposable = new FromArrayDisposable<T>(observer, this.array);
        observer.onSubscribe(fromArrayDisposable);
        if (fromArrayDisposable.fusionMode) {
            return;
        }
        fromArrayDisposable.run();
    }

    static final class FromArrayDisposable<T>
    extends BasicQueueDisposable<T> {
        final Observer<? super T> actual;
        final T[] array;
        volatile boolean disposed;
        boolean fusionMode;
        int index;

        FromArrayDisposable(Observer<? super T> observer, T[] arrT) {
            this.actual = observer;
            this.array = arrT;
        }

        @Override
        public void clear() {
            this.index = this.array.length;
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
            return this.index == this.array.length;
        }

        @Override
        public T poll() {
            int n = this.index;
            T[] arrT = this.array;
            if (n != arrT.length) {
                this.index = n + 1;
                return ObjectHelper.requireNonNull(arrT[n], "The array element is null");
            }
            return null;
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
         */
        void run() {
            T[] arrT = this.array;
            int n = arrT.length;
            for (int i = 0; i < n && !this.isDisposed(); ++i) {
                T t = arrT[i];
                if (t == null) {
                    this.actual.onError(new NullPointerException("The " + i + "th element is null"));
                    return;
                }
                this.actual.onNext(t);
            }
            if (this.isDisposed()) return;
            {
                this.actual.onComplete();
                return;
            }
        }
    }

}

