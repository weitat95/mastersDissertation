/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import java.util.Iterator;

public final class SingleFlatMapIterableObservable<T, R>
extends Observable<R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final SingleSource<T> source;

    public SingleFlatMapIterableObservable(SingleSource<T> singleSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        this.source = singleSource;
        this.mapper = function;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapIterableObserver<T, R>(observer, this.mapper));
    }

    static final class FlatMapIterableObserver<T, R>
    extends BasicIntQueueDisposable<R>
    implements SingleObserver<T> {
        final Observer<? super R> actual;
        volatile boolean cancelled;
        Disposable d;
        volatile Iterator<? extends R> it;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        boolean outputFused;

        FlatMapIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.actual = observer;
            this.mapper = function;
        }

        @Override
        public void clear() {
            this.it = null;
        }

        @Override
        public void dispose() {
            this.cancelled = true;
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override
        public boolean isEmpty() {
            return this.it == null;
        }

        @Override
        public void onError(Throwable throwable) {
            this.d = DisposableHelper.DISPOSED;
            this.actual.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onSuccess(T var1_1) {
            block9: {
                var3_5 = this.actual;
                try {
                    var1_1 = this.mapper.apply(var1_1).iterator();
                    var2_6 = var1_1.hasNext();
                    if (var2_6) break block9;
                    var3_5.onComplete();
                }
                catch (Throwable var1_2) {
                    Exceptions.throwIfFatal(var1_2);
                    this.actual.onError(var1_2);
                    return;
                }
lbl7:
                // 3 sources
                do {
                    return;
                    break;
                } while (true);
            }
            if (this.outputFused) {
                this.it = var1_1;
                var3_5.onNext(null);
                var3_5.onComplete();
                return;
            }
            do {
                if (this.cancelled) ** GOTO lbl7
                try {
                    var4_7 = var1_1.next();
                    var3_5.onNext(var4_7);
                    if (this.cancelled) ** continue;
                }
                catch (Throwable var1_3) {
                    Exceptions.throwIfFatal(var1_3);
                    var3_5.onError(var1_3);
                    return;
                }
                try {}
                catch (Throwable var1_4) {
                    Exceptions.throwIfFatal(var1_4);
                    var3_5.onError(var1_4);
                    return;
                }
            } while (var2_6 = var1_1.hasNext());
            var3_5.onComplete();
        }

        @Override
        public R poll() throws Exception {
            Iterator<R> iterator = this.it;
            if (iterator != null) {
                R r = ObjectHelper.requireNonNull(iterator.next(), "The iterator returned a null value");
                if (!iterator.hasNext()) {
                    this.it = null;
                }
                return r;
            }
            return null;
        }

        @Override
        public int requestFusion(int n) {
            if ((n & 2) != 0) {
                this.outputFused = true;
                return 2;
            }
            return 0;
        }
    }

}

