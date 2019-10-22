/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.internal.operators.observable.AbstractObservableWithUpstream;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableDistinct<T, K>
extends AbstractObservableWithUpstream<T, T> {
    final Callable<? extends Collection<? super K>> collectionSupplier;
    final Function<? super T, K> keySelector;

    public ObservableDistinct(ObservableSource<T> observableSource, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(observableSource);
        this.keySelector = function;
        this.collectionSupplier = callable;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        try {
            Collection<? super K> collection = ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
            this.source.subscribe(new DistinctObserver<T, K>(observer, this.keySelector, collection));
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptyDisposable.error(throwable, observer);
            return;
        }
    }

    static final class DistinctObserver<T, K>
    extends BasicFuseableObserver<T, T> {
        final Collection<? super K> collection;
        final Function<? super T, K> keySelector;

        DistinctObserver(Observer<? super T> observer, Function<? super T, K> function, Collection<? super K> collection) {
            super(observer);
            this.keySelector = function;
            this.collection = collection;
        }

        @Override
        public void clear() {
            this.collection.clear();
            super.clear();
        }

        @Override
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.collection.clear();
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (this.done) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            this.done = true;
            this.collection.clear();
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
                if (this.sourceMode != 0) {
                    this.actual.onNext(null);
                    return;
                }
                try {
                    K k = ObjectHelper.requireNonNull(this.keySelector.apply(t), "The keySelector returned a null key");
                    boolean bl = this.collection.add(k);
                    if (!bl) return;
                    {
                        this.actual.onNext(t);
                        return;
                    }
                }
                catch (Throwable throwable) {
                    this.fail(throwable);
                    return;
                }
            }
        }

        @Override
        public T poll() throws Exception {
            Object t;
            while ((t = this.qs.poll()) != null && !this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply(t), "The keySelector returned a null key"))) {
            }
            return t;
        }

        @Override
        public int requestFusion(int n) {
            return this.transitiveBoundaryFusion(n);
        }
    }

}

