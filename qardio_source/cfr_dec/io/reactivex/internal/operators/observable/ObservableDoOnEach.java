/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDoOnEach<T>
extends AbstractObservableWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;

    public ObservableDoOnEach(ObservableSource<T> observableSource, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        super(observableSource);
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onAfterTerminate = action2;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DoOnEachObserver<T>(observer, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
    }

    static final class DoOnEachObserver<T>
    implements Observer<T>,
    Disposable {
        final Observer<? super T> actual;
        boolean done;
        final Action onAfterTerminate;
        final Action onComplete;
        final Consumer<? super Throwable> onError;
        final Consumer<? super T> onNext;
        Disposable s;

        DoOnEachObserver(Observer<? super T> observer, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            this.actual = observer;
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
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
            if (this.done) {
                return;
            }
            try {
                this.onComplete.run();
                this.done = true;
                this.actual.onComplete();
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.onError(throwable);
                return;
            }
            try {
                this.onAfterTerminate.run();
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                RxJavaPlugins.onError(throwable);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            try {
                this.onError.accept(throwable);
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                throwable = new CompositeException(throwable, throwable2);
            }
            this.actual.onError(throwable);
            try {
                this.onAfterTerminate.run();
                return;
            }
            catch (Throwable throwable3) {
                Exceptions.throwIfFatal(throwable3);
                RxJavaPlugins.onError(throwable3);
                return;
            }
        }

        @Override
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            try {
                this.onNext.accept(t);
                this.actual.onNext(t);
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.s.dispose();
                this.onError(throwable);
                return;
            }
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

