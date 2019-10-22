/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import io.reactivex.internal.observers.EmptyCompletableObserver;
import io.reactivex.internal.operators.completable.CompletableCreate;
import io.reactivex.internal.operators.completable.CompletableError;
import io.reactivex.internal.operators.completable.CompletableFromRunnable;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.internal.operators.completable.CompletableObserveOn;
import io.reactivex.internal.operators.completable.CompletableOnErrorComplete;
import io.reactivex.internal.operators.completable.CompletablePeek;
import io.reactivex.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class Completable
implements CompletableSource {
    public static Completable create(CompletableOnSubscribe completableOnSubscribe) {
        ObjectHelper.requireNonNull(completableOnSubscribe, "source is null");
        return RxJavaPlugins.onAssembly(new CompletableCreate(completableOnSubscribe));
    }

    private Completable doOnLifecycle(Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        ObjectHelper.requireNonNull(consumer, "onSubscribe is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(action2, "onTerminate is null");
        ObjectHelper.requireNonNull(action3, "onAfterTerminate is null");
        ObjectHelper.requireNonNull(action4, "onDispose is null");
        return RxJavaPlugins.onAssembly(new CompletablePeek(this, consumer, consumer2, action, action2, action3, action4));
    }

    public static Completable error(Throwable throwable) {
        ObjectHelper.requireNonNull(throwable, "error is null");
        return RxJavaPlugins.onAssembly(new CompletableError(throwable));
    }

    public static Completable fromRunnable(Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "run is null");
        return RxJavaPlugins.onAssembly(new CompletableFromRunnable(runnable));
    }

    public static <T> Completable fromSingle(SingleSource<T> singleSource) {
        ObjectHelper.requireNonNull(singleSource, "single is null");
        return RxJavaPlugins.onAssembly(new CompletableFromSingle<T>(singleSource));
    }

    private static NullPointerException toNpe(Throwable throwable) {
        NullPointerException nullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        nullPointerException.initCause(throwable);
        return nullPointerException;
    }

    public static Completable wrap(CompletableSource completableSource) {
        ObjectHelper.requireNonNull(completableSource, "source is null");
        if (completableSource instanceof Completable) {
            return RxJavaPlugins.onAssembly((Completable)completableSource);
        }
        return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(completableSource));
    }

    public final Completable compose(CompletableTransformer completableTransformer) {
        return Completable.wrap(ObjectHelper.requireNonNull(completableTransformer, "transformer is null").apply(this));
    }

    public final Completable doOnError(Consumer<? super Throwable> consumer) {
        return this.doOnLifecycle(Functions.emptyConsumer(), consumer, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    public final Completable observeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableObserveOn(this, scheduler));
    }

    public final Completable onErrorComplete() {
        return this.onErrorComplete(Functions.alwaysTrue());
    }

    public final Completable onErrorComplete(Predicate<? super Throwable> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new CompletableOnErrorComplete(this, predicate));
    }

    public final Disposable subscribe() {
        EmptyCompletableObserver emptyCompletableObserver = new EmptyCompletableObserver();
        this.subscribe(emptyCompletableObserver);
        return emptyCompletableObserver;
    }

    public final Disposable subscribe(Action object) {
        ObjectHelper.requireNonNull(object, "onComplete is null");
        object = new CallbackCompletableObserver((Action)object);
        this.subscribe((CompletableObserver)object);
        return object;
    }

    public final Disposable subscribe(Action object, Consumer<? super Throwable> consumer) {
        ObjectHelper.requireNonNull(consumer, "onError is null");
        ObjectHelper.requireNonNull(object, "onComplete is null");
        object = new CallbackCompletableObserver(consumer, (Action)object);
        this.subscribe((CompletableObserver)object);
        return object;
    }

    @Override
    public final void subscribe(CompletableObserver completableObserver) {
        ObjectHelper.requireNonNull(completableObserver, "s is null");
        try {
            this.subscribeActual(RxJavaPlugins.onSubscribe(this, completableObserver));
            return;
        }
        catch (NullPointerException nullPointerException) {
            throw nullPointerException;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            RxJavaPlugins.onError(throwable);
            throw Completable.toNpe(throwable);
        }
    }

    protected abstract void subscribeActual(CompletableObserver var1);

    public final Completable subscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new CompletableSubscribeOn(this, scheduler));
    }
}

