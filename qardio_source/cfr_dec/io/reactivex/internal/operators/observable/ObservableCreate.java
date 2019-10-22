/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCreate<T>
extends Observable<T> {
    final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.source = observableOnSubscribe;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> createEmitter = new CreateEmitter<T>(observer);
        observer.onSubscribe(createEmitter);
        try {
            this.source.subscribe(createEmitter);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            createEmitter.onError(throwable);
            return;
        }
    }

    static final class CreateEmitter<T>
    extends AtomicReference<Disposable>
    implements ObservableEmitter<T>,
    Disposable {
        final Observer<? super T> observer;

        CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
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
            if (!this.isDisposed()) {
                this.observer.onComplete();
            }
            return;
            finally {
                this.dispose();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!this.tryOnError(throwable)) {
                RxJavaPlugins.onError(throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onNext(T t) {
            if (t == null) {
                this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            } else {
                if (this.isDisposed()) return;
                {
                    this.observer.onNext(t);
                    return;
                }
            }
        }

        public boolean tryOnError(Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable == null) {
                throwable2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (!this.isDisposed()) {
                try {
                    this.observer.onError(throwable2);
                    return true;
                }
                finally {
                    this.dispose();
                }
            }
            return false;
        }
    }

}

