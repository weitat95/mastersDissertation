/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableScalarXMap {
    public static <T, U> Observable<U> scalarXMap(T t, Function<? super T, ? extends ObservableSource<? extends U>> function) {
        return RxJavaPlugins.onAssembly(new ScalarXMapObservable((T)t, function));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T, R> boolean tryScalarXMapSubscribe(ObservableSource<T> object, Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function) {
        block9: {
            if (!(object instanceof Callable)) {
                return false;
            }
            try {
                object = ((Callable)object).call();
                if (object != null) break block9;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptyDisposable.error(throwable, observer);
                return true;
            }
            EmptyDisposable.complete(observer);
            return true;
        }
        try {
            object = ObjectHelper.requireNonNull(function.apply((ObservableSource<ObservableSource<R>>)object), "The mapper returned a null ObservableSource");
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptyDisposable.error(throwable, observer);
            return true;
        }
        if (!(object instanceof Callable)) {
            object.subscribe(observer);
            return true;
        }
        try {}
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptyDisposable.error(throwable, observer);
            return true;
        }
        object = ((Callable)object).call();
        if (object == null) {
            EmptyDisposable.complete(observer);
            return true;
        }
        object = new ScalarDisposable<ObservableSource<R>>((Observer<ObservableSource<R>>)observer, (ObservableSource<R>)object);
        observer.onSubscribe((Disposable)object);
        ((ScalarDisposable)object).run();
        return true;
    }

    public static final class ScalarDisposable<T>
    extends AtomicInteger
    implements QueueDisposable<T>,
    Runnable {
        final Observer<? super T> observer;
        final T value;

        public ScalarDisposable(Observer<? super T> observer, T t) {
            this.observer = observer;
            this.value = t;
        }

        @Override
        public void clear() {
            this.lazySet(3);
        }

        @Override
        public void dispose() {
            this.set(3);
        }

        @Override
        public boolean isDisposed() {
            return this.get() == 3;
        }

        @Override
        public boolean isEmpty() {
            return this.get() != 1;
        }

        @Override
        public boolean offer(T t) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        @Override
        public T poll() throws Exception {
            if (this.get() == 1) {
                this.lazySet(3);
                return this.value;
            }
            return null;
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 1) != 0) {
                this.lazySet(1);
                return 1;
            }
            return 0;
        }

        @Override
        public void run() {
            if (this.get() == 0 && this.compareAndSet(0, 2)) {
                this.observer.onNext(this.value);
                if (this.get() == 2) {
                    this.lazySet(3);
                    this.observer.onComplete();
                }
            }
        }
    }

    static final class ScalarXMapObservable<T, R>
    extends Observable<R> {
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final T value;

        ScalarXMapObservable(T t, Function<? super T, ? extends ObservableSource<? extends R>> function) {
            this.value = t;
            this.mapper = function;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void subscribeActual(Observer<? super R> observer) {
            Object object;
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(this.value), "The mapper returned a null ObservableSource");
            }
            catch (Throwable throwable) {
                EmptyDisposable.error(throwable, observer);
                return;
            }
            if (!(object instanceof Callable)) {
                object.subscribe(observer);
                return;
            }
            try {}
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptyDisposable.error(throwable, observer);
                return;
            }
            object = ((Callable)object).call();
            if (object == null) {
                EmptyDisposable.complete(observer);
                return;
            }
            object = new ScalarDisposable<ObservableSource<? extends R>>((Observer<ObservableSource<? extends R>>)observer, (ObservableSource<? extends R>)object);
            observer.onSubscribe((Disposable)object);
            ((ScalarDisposable)object).run();
        }
    }

}

