/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableToList<T, U extends Collection<? super T>>
extends AbstractObservableWithUpstream<T, U> {
    final Callable<U> collectionSupplier;

    public ObservableToList(ObservableSource<T> observableSource, Callable<U> callable) {
        super(observableSource);
        this.collectionSupplier = callable;
    }

    @Override
    public void subscribeActual(Observer<? super U> observer) {
        try {
            Collection collection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
            this.source.subscribe(new ToListObserver(observer, collection));
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptyDisposable.error(throwable, observer);
            return;
        }
    }

    static final class ToListObserver<T, U extends Collection<? super T>>
    implements Observer<T>,
    Disposable {
        final Observer<? super U> actual;
        U collection;
        Disposable s;

        ToListObserver(Observer<? super U> observer, U u) {
            this.actual = observer;
            this.collection = u;
        }

        @Override
        public void dispose() {
            this.s.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.s.isDisposed();
        }

        @Override
        public void onComplete() {
            U u = this.collection;
            this.collection = null;
            this.actual.onNext(u);
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.collection = null;
            this.actual.onError(throwable);
        }

        @Override
        public void onNext(T t) {
            this.collection.add(t);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

}

