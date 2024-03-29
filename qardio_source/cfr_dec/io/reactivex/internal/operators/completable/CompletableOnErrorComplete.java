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
import io.reactivex.functions.Predicate;

public final class CompletableOnErrorComplete
extends Completable {
    final Predicate<? super Throwable> predicate;
    final CompletableSource source;

    public CompletableOnErrorComplete(CompletableSource completableSource, Predicate<? super Throwable> predicate) {
        this.source = completableSource;
        this.predicate = predicate;
    }

    @Override
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new OnError(completableObserver));
    }

    final class OnError
    implements CompletableObserver {
        private final CompletableObserver s;

        OnError(CompletableObserver completableObserver) {
            this.s = completableObserver;
        }

        @Override
        public void onComplete() {
            this.s.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                boolean bl = CompletableOnErrorComplete.this.predicate.test(throwable);
                if (bl) {
                    this.s.onComplete();
                    return;
                }
            }
            catch (Throwable throwable2) {
                Exceptions.throwIfFatal(throwable2);
                this.s.onError(new CompositeException(throwable, throwable2));
                return;
            }
            this.s.onError(throwable);
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.s.onSubscribe(disposable);
        }
    }

}

