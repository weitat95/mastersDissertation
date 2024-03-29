/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;

public final class ObservableFlattenIterable<T, R>
extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;

    public ObservableFlattenIterable(ObservableSource<T> observableSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        super(observableSource);
        this.mapper = function;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlattenIterableObserver<T, R>(observer, this.mapper));
    }

    static final class FlattenIterableObserver<T, R>
    implements Observer<T>,
    Disposable {
        final Observer<? super R> actual;
        Disposable d;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;

        FlattenIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.actual = observer;
            this.mapper = function;
        }

        @Override
        public void dispose() {
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
        }

        @Override
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override
        public void onComplete() {
            if (this.d == DisposableHelper.DISPOSED) {
                return;
            }
            this.d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.d = DisposableHelper.DISPOSED;
            this.actual.onError(throwable);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void onNext(T object) {
            Observer<R> observer;
            if (this.d == DisposableHelper.DISPOSED) {
                return;
            }
            try {
                object = this.mapper.apply(object).iterator();
                observer = this.actual;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.d.dispose();
                this.onError(throwable);
                return;
            }
            do {
                boolean bl = object.hasNext();
                if (!bl) return;
                break;
            } while (true);
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.d.dispose();
                this.onError(throwable);
                return;
            }
            {
                Object e = ObjectHelper.requireNonNull(object.next(), "The iterator returned a null value");
                observer.onNext(e);
                continue;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.d.dispose();
                this.onError(throwable);
                return;
            }
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

}

