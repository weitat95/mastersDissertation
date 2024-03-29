/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletablePeek
extends Completable {
    final Action onAfterTerminate;
    final Action onComplete;
    final Action onDispose;
    final Consumer<? super Throwable> onError;
    final Consumer<? super Disposable> onSubscribe;
    final Action onTerminate;
    final CompletableSource source;

    public CompletablePeek(CompletableSource completableSource, Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        this.source = completableSource;
        this.onSubscribe = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onTerminate = action2;
        this.onAfterTerminate = action3;
        this.onDispose = action4;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new CompletableObserverImplementation(completableObserver));
    }

    final class CompletableObserverImplementation
    implements CompletableObserver,
    Disposable {
        final CompletableObserver actual;
        Disposable d;

        CompletableObserverImplementation(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispose() {
            try {
                CompletablePeek.this.onDispose.run();
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                RxJavaPlugins.onError(throwable);
            }
            this.d.dispose();
        }

        void doAfter() {
            try {
                CompletablePeek.this.onAfterTerminate.run();
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                RxJavaPlugins.onError(throwable);
                return;
            }
        }

        @Override
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override
        public void onComplete() {
            if (this.d == DisposableHelper.DISPOSED) {
                return;
            }
            try {
                CompletablePeek.this.onComplete.run();
                CompletablePeek.this.onTerminate.run();
                this.actual.onComplete();
                this.doAfter();
                return;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                this.actual.onError(throwable);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onError(Throwable throwable) {
            if (this.d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(throwable);
                return;
            }
            try {
                CompletablePeek.this.onError.accept(throwable);
                CompletablePeek.this.onTerminate.run();
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                throwable = new CompositeException(throwable, throwable2);
            }
            this.actual.onError(throwable);
            this.doAfter();
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            try {
                CompletablePeek.this.onSubscribe.accept(disposable);
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                disposable.dispose();
                this.d = DisposableHelper.DISPOSED;
                EmptyDisposable.error(throwable, this.actual);
                return;
            }
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

}

