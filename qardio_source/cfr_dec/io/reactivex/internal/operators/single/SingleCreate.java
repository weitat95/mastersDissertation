/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCreate<T>
extends Single<T> {
    final SingleOnSubscribe<T> source;

    public SingleCreate(SingleOnSubscribe<T> singleOnSubscribe) {
        this.source = singleOnSubscribe;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        Emitter<T> emitter = new Emitter<T>(singleObserver);
        singleObserver.onSubscribe(emitter);
        try {
            this.source.subscribe(emitter);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            emitter.onError(throwable);
            return;
        }
    }

    static final class Emitter<T>
    extends AtomicReference<Disposable>
    implements SingleEmitter<T>,
    Disposable {
        final SingleObserver<? super T> actual;

        Emitter(SingleObserver<? super T> singleObserver) {
            this.actual = singleObserver;
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable)this.get());
        }

        @Override
        public void onError(Throwable throwable) {
            if (!this.tryOnError(throwable)) {
                RxJavaPlugins.onError(throwable);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onSuccess(T t) {
            Disposable disposable;
            block10: {
                block9: {
                    if (this.get() == DisposableHelper.DISPOSED || (disposable = (Disposable)this.getAndSet(DisposableHelper.DISPOSED)) == DisposableHelper.DISPOSED) break block9;
                    if (t != null) break block10;
                    this.actual.onError(new NullPointerException("onSuccess called with null. Null values are generally not allowed in 2.x operators and sources."));
                }
                do {
                    return;
                    break;
                } while (true);
            }
            try {
                this.actual.onSuccess(t);
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }

        @Override
        public void setCancellable(Cancellable cancellable) {
            this.setDisposable(new CancellableDisposable(cancellable));
        }

        public void setDisposable(Disposable disposable) {
            DisposableHelper.set(this, disposable);
        }

        public boolean tryOnError(Throwable object) {
            Throwable throwable = object;
            if (object == null) {
                throwable = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (this.get() != DisposableHelper.DISPOSED && (object = (Disposable)this.getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                try {
                    this.actual.onError(throwable);
                    return true;
                }
                finally {
                    if (object != null) {
                        object.dispose();
                    }
                }
            }
            return false;
        }
    }

}

