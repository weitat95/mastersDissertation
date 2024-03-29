/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;

public final class ObservableFilter<T>
extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.predicate = predicate;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FilterObserver<T>(observer, this.predicate));
    }

    static final class FilterObserver<T>
    extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        FilterObserver(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.filter = predicate;
        }

        @Override
        public void onNext(T t) {
            if (this.sourceMode == 0) {
                try {
                    boolean bl = this.filter.test(t);
                    if (bl) {
                        this.actual.onNext(t);
                    }
                    return;
                }
                catch (Throwable throwable) {
                    this.fail(throwable);
                    return;
                }
            }
            this.actual.onNext(null);
        }

        @Override
        public T poll() throws Exception {
            Object t;
            while ((t = this.qs.poll()) != null && !this.filter.test(t)) {
            }
            return t;
        }

        @Override
        public int requestFusion(int n) {
            return this.transitiveBoundaryFusion(n);
        }
    }

}

