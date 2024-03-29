/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatten<T, R>
extends AbstractMaybeWithUpstream<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

    public MaybeFlatten(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends R>> function) {
        super(maybeSource);
        this.mapper = function;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new FlatMapMaybeObserver<T, R>(maybeObserver, this.mapper));
    }

    static final class FlatMapMaybeObserver<T, R>
    extends AtomicReference<Disposable>
    implements MaybeObserver<T>,
    Disposable {
        final MaybeObserver<? super R> actual;
        Disposable d;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

        FlatMapMaybeObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function) {
            this.actual = maybeObserver;
            this.mapper = function;
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
            this.d.dispose();
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
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
        public void onSuccess(T object) {
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper returned a null MaybeSource");
                if (!this.isDisposed()) {
                    object.subscribe(new InnerObserver());
                }
                return;
            }
            catch (Exception exception) {
                Exceptions.throwIfFatal(exception);
                this.actual.onError(exception);
                return;
            }
        }

        final class InnerObserver
        implements MaybeObserver<R> {
            InnerObserver() {
            }

            @Override
            public void onComplete() {
                FlatMapMaybeObserver.this.actual.onComplete();
            }

            @Override
            public void onError(Throwable throwable) {
                FlatMapMaybeObserver.this.actual.onError(throwable);
            }

            @Override
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(FlatMapMaybeObserver.this, disposable);
            }

            @Override
            public void onSuccess(R r) {
                FlatMapMaybeObserver.this.actual.onSuccess(r);
            }
        }

    }

}

