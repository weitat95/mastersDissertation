/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleZipArray<T, R>
extends Single<R> {
    final SingleSource<? extends T>[] sources;
    final Function<? super Object[], ? extends R> zipper;

    public SingleZipArray(SingleSource<? extends T>[] arrsingleSource, Function<? super Object[], ? extends R> function) {
        this.sources = arrsingleSource;
        this.zipper = function;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void subscribeActual(SingleObserver<? super R> singleSource) {
        SingleSource<? extends T>[] arrsingleSource = this.sources;
        int n = arrsingleSource.length;
        if (n == 1) {
            arrsingleSource[0].subscribe(new SingleMap.MapSingleObserver((SingleObserver<Object>)((Object)singleSource), new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator((SingleObserver<R>)((Object)singleSource), n, (Function<Object[], R>)this.zipper);
        singleSource.onSubscribe(zipCoordinator);
        int n2 = 0;
        while (n2 < n && !zipCoordinator.isDisposed()) {
            singleSource = arrsingleSource[n2];
            if (singleSource == null) {
                zipCoordinator.innerError(new NullPointerException("One of the sources is null"), n2);
                return;
            }
            singleSource.subscribe(zipCoordinator.observers[n2]);
            ++n2;
        }
        return;
    }

    final class SingletonArrayFunc
    implements Function<T, R> {
        SingletonArrayFunc() {
        }

        @Override
        public R apply(T t) throws Exception {
            return ObjectHelper.requireNonNull(SingleZipArray.this.zipper.apply((Object[])new Object[]{t}), "The zipper returned a null value");
        }
    }

    static final class ZipCoordinator<T, R>
    extends AtomicInteger
    implements Disposable {
        final SingleObserver<? super R> actual;
        final ZipSingleObserver<T>[] observers;
        final Object[] values;
        final Function<? super Object[], ? extends R> zipper;

        ZipCoordinator(SingleObserver<? super R> arrzipSingleObserver, int n, Function<? super Object[], ? extends R> function) {
            super(n);
            this.actual = arrzipSingleObserver;
            this.zipper = function;
            arrzipSingleObserver = new ZipSingleObserver[n];
            for (int i = 0; i < n; ++i) {
                arrzipSingleObserver[i] = new ZipSingleObserver(this, i);
            }
            this.observers = arrzipSingleObserver;
            this.values = new Object[n];
        }

        @Override
        public void dispose() {
            if (this.getAndSet(0) > 0) {
                ZipSingleObserver<T>[] arrzipSingleObserver = this.observers;
                int n = arrzipSingleObserver.length;
                for (int i = 0; i < n; ++i) {
                    arrzipSingleObserver[i].dispose();
                }
            }
        }

        void disposeExcept(int n) {
            ZipSingleObserver<T>[] arrzipSingleObserver = this.observers;
            int n2 = arrzipSingleObserver.length;
            for (int i = 0; i < n; ++i) {
                arrzipSingleObserver[i].dispose();
            }
            ++n;
            while (n < n2) {
                arrzipSingleObserver[n].dispose();
                ++n;
            }
        }

        void innerError(Throwable throwable, int n) {
            if (this.getAndSet(0) > 0) {
                this.disposeExcept(n);
                this.actual.onError(throwable);
                return;
            }
            RxJavaPlugins.onError(throwable);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void innerSuccess(T object, int n) {
            this.values[n] = object;
            if (this.decrementAndGet() != 0) return;
            try {
                object = ObjectHelper.requireNonNull(this.zipper.apply((Object[])this.values), "The zipper returned a null value");
                this.actual.onSuccess(object);
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.actual.onError(throwable);
                return;
            }
        }

        @Override
        public boolean isDisposed() {
            return this.get() <= 0;
        }
    }

    static final class ZipSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T> {
        final int index;
        final ZipCoordinator<T, ?> parent;

        ZipSingleObserver(ZipCoordinator<T, ?> zipCoordinator, int n) {
            this.parent = zipCoordinator;
            this.index = n;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public void onError(Throwable throwable) {
            this.parent.innerError(throwable, this.index);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override
        public void onSuccess(T t) {
            this.parent.innerSuccess(t, this.index);
        }
    }

}

