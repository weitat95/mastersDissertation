/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableCreate
extends Completable {
    final CompletableOnSubscribe source;

    public CompletableCreate(CompletableOnSubscribe completableOnSubscribe) {
        this.source = completableOnSubscribe;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        Emitter emitter = new Emitter(completableObserver);
        completableObserver.onSubscribe(emitter);
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

    static final class Emitter
    extends AtomicReference<Disposable>
    implements CompletableEmitter,
    Disposable {
        final CompletableObserver actual;

        Emitter(CompletableObserver completableObserver) {
            this.actual = completableObserver;
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
        public void onComplete() {
            Disposable disposable;
            if (this.get() != DisposableHelper.DISPOSED && (disposable = (Disposable)this.getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                this.actual.onComplete();
            }
            return;
            finally {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }

        public void onError(Throwable throwable) {
            if (!this.tryOnError(throwable)) {
                RxJavaPlugins.onError(throwable);
            }
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

