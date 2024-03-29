/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;

public final class ObservableMap<T, U>
extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends U> function;

    public ObservableMap(ObservableSource<T> observableSource, Function<? super T, ? extends U> function) {
        super(observableSource);
        this.function = function;
    }

    @Override
    public void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new MapObserver<T, U>(observer, this.function));
    }

    static final class MapObserver<T, U>
    extends BasicFuseableObserver<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapObserver(Observer<? super U> observer, Function<? super T, ? extends U> function) {
            super(observer);
            this.mapper = function;
        }

        @Override
        public void onNext(T object) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(null);
                return;
            }
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper function returned a null value.");
                this.actual.onNext(object);
                return;
            }
            catch (Throwable throwable) {
                this.fail(throwable);
                return;
            }
        }

        @Override
        public U poll() throws Exception {
            Object t = this.qs.poll();
            if (t != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }

        @Override
        public int requestFusion(int n) {
            return this.transitiveBoundaryFusion(n);
        }
    }

}

