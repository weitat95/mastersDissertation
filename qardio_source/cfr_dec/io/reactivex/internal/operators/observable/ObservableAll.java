/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAll<T>
extends AbstractObservableWithUpstream<T, Boolean> {
    final Predicate<? super T> predicate;

    public ObservableAll(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.predicate = predicate;
    }

    @Override
    protected void subscribeActual(Observer<? super Boolean> observer) {
        this.source.subscribe(new AllObserver<T>(observer, this.predicate));
    }

    static final class AllObserver<T>
    implements Observer<T>,
    Disposable {
        final Observer<? super Boolean> actual;
        boolean done;
        final Predicate<? super T> predicate;
        Disposable s;

        AllObserver(Observer<? super Boolean> observer, Predicate<? super T> predicate) {
            this.actual = observer;
            this.predicate = predicate;
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
            this.done = true;
            this.actual.onNext((Boolean)true);
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNext(T t) {
            if (this.done) return;
            {
                try {
                    boolean bl = this.predicate.test(t);
                    if (bl) return;
                    {
                        this.done = true;
                        this.s.dispose();
                        this.actual.onNext((Boolean)false);
                        this.actual.onComplete();
                        return;
                    }
                }
                catch (Throwable throwable) {
                    Exceptions.throwIfFatal(throwable);
                    this.s.dispose();
                    this.onError(throwable);
                    return;
                }
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

