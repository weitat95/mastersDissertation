/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream;

public final class MaybeFilter<T>
extends AbstractMaybeWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    public MaybeFilter(MaybeSource<T> maybeSource, Predicate<? super T> predicate) {
        super(maybeSource);
        this.predicate = predicate;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new FilterMaybeObserver<T>(maybeObserver, this.predicate));
    }

    static final class FilterMaybeObserver<T>
    implements MaybeObserver<T>,
    Disposable {
        final MaybeObserver<? super T> actual;
        Disposable d;
        final Predicate<? super T> predicate;

        FilterMaybeObserver(MaybeObserver<? super T> maybeObserver, Predicate<? super T> predicate) {
            this.actual = maybeObserver;
            this.predicate = predicate;
        }

        @Override
        public void dispose() {
            Disposable disposable = this.d;
            this.d = DisposableHelper.DISPOSED;
            disposable.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.actual.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T t) {
            try {
                boolean bl = this.predicate.test(t);
                if (bl) {
                    this.actual.onSuccess(t);
                    return;
                }
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.actual.onError(throwable);
                return;
            }
            this.actual.onComplete();
        }
    }

}

