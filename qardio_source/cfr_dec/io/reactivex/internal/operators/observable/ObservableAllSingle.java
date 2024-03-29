/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.operators.observable.ObservableAll;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAllSingle<T>
extends Single<Boolean>
implements FuseToObservable<Boolean> {
    final Predicate<? super T> predicate;
    final ObservableSource<T> source;

    public ObservableAllSingle(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        this.source = observableSource;
        this.predicate = predicate;
    }

    @Override
    public Observable<Boolean> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableAll<T>(this.source, this.predicate));
    }

    @Override
    protected void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.source.subscribe(new AllObserver<T>(singleObserver, this.predicate));
    }

    static final class AllObserver<T>
    implements Observer<T>,
    Disposable {
        final SingleObserver<? super Boolean> actual;
        boolean done;
        final Predicate<? super T> predicate;
        Disposable s;

        AllObserver(SingleObserver<? super Boolean> singleObserver, Predicate<? super T> predicate) {
            this.actual = singleObserver;
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
            this.actual.onSuccess((Boolean)true);
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
                        this.actual.onSuccess((Boolean)false);
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

