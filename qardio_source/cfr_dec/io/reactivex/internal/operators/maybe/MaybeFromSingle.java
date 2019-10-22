/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeFromSingle<T>
extends Maybe<T> {
    final SingleSource<T> source;

    public MaybeFromSingle(SingleSource<T> singleSource) {
        this.source = singleSource;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new FromSingleObserver<T>(maybeObserver));
    }

    static final class FromSingleObserver<T>
    implements SingleObserver<T>,
    Disposable {
        final MaybeObserver<? super T> actual;
        Disposable d;

        FromSingleObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
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

        @Override
        public void onSuccess(T t) {
            this.d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(t);
        }
    }

}

