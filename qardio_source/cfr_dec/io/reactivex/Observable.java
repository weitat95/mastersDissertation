/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.internal.operators.observable.ObservableAllSingle;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.internal.operators.observable.ObservableDefer;
import io.reactivex.internal.operators.observable.ObservableDistinct;
import io.reactivex.internal.operators.observable.ObservableDoOnEach;
import io.reactivex.internal.operators.observable.ObservableEmpty;
import io.reactivex.internal.operators.observable.ObservableFilter;
import io.reactivex.internal.operators.observable.ObservableFlatMap;
import io.reactivex.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.internal.operators.observable.ObservableFlattenIterable;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.internal.operators.observable.ObservableFromUnsafeSource;
import io.reactivex.internal.operators.observable.ObservableIgnoreElementsCompletable;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.internal.operators.observable.ObservableMap;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableRetryPredicate;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.internal.operators.observable.ObservableSingleMaybe;
import io.reactivex.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.internal.operators.observable.ObservableToListSingle;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Observable<T>
implements ObservableSource<T> {
    public static int bufferSize() {
        return Flowable.bufferSize();
    }

    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        ObjectHelper.requireNonNull(observableOnSubscribe, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableCreate<T>(observableOnSubscribe));
    }

    public static <T> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> callable) {
        ObjectHelper.requireNonNull(callable, "supplier is null");
        return RxJavaPlugins.onAssembly(new ObservableDefer(callable));
    }

    private Observable<T> doOnEach(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        ObjectHelper.requireNonNull(consumer, "onNext is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(action2, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new ObservableDoOnEach<T>(this, consumer, consumer2, action, action2));
    }

    public static <T> Observable<T> empty() {
        return RxJavaPlugins.onAssembly(ObservableEmpty.INSTANCE);
    }

    public static <T> Observable<T> fromArray(T ... arrT) {
        ObjectHelper.requireNonNull(arrT, "items is null");
        if (arrT.length == 0) {
            return Observable.empty();
        }
        if (arrT.length == 1) {
            return Observable.just(arrT[0]);
        }
        return RxJavaPlugins.onAssembly(new ObservableFromArray<T>(arrT));
    }

    public static <T> Observable<T> fromIterable(Iterable<? extends T> iterable) {
        ObjectHelper.requireNonNull(iterable, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableFromIterable<T>(iterable));
    }

    public static <T> Observable<T> just(T t) {
        ObjectHelper.requireNonNull(t, "The item is null");
        return RxJavaPlugins.onAssembly(new ObservableJust<T>(t));
    }

    public static <T> Observable<T> wrap(ObservableSource<T> observableSource) {
        ObjectHelper.requireNonNull(observableSource, "source is null");
        if (observableSource instanceof Observable) {
            return RxJavaPlugins.onAssembly((Observable)observableSource);
        }
        return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource<T>(observableSource));
    }

    public final Single<Boolean> all(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableAllSingle<T>(this, predicate));
    }

    public final <R> Observable<R> compose(ObservableTransformer<? super T, ? extends R> observableTransformer) {
        return Observable.wrap(ObjectHelper.requireNonNull(observableTransformer, "composer is null").apply(this));
    }

    public final Observable<T> distinct() {
        return this.distinct(Functions.identity(), Functions.createHashSet());
    }

    public final <K> Observable<T> distinct(Function<? super T, K> function) {
        return this.distinct(function, Functions.createHashSet());
    }

    public final <K> Observable<T> distinct(Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(callable, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new ObservableDistinct<T, K>(this, function, callable));
    }

    public final Observable<T> doOnError(Consumer<? super Throwable> consumer) {
        return this.doOnEach(Functions.emptyConsumer(), consumer, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    public final Observable<T> doOnNext(Consumer<? super T> consumer) {
        return this.doOnEach(consumer, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
    }

    public final Observable<T> doOnTerminate(Action action) {
        ObjectHelper.requireNonNull(action, "onTerminate is null");
        return this.doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(action), action, Functions.EMPTY_ACTION);
    }

    public final Observable<T> filter(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableFilter<T>(this, predicate));
    }

    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> function) {
        return this.flatMap(function, false);
    }

    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> function, boolean bl) {
        return this.flatMap(function, bl, Integer.MAX_VALUE);
    }

    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> function, boolean bl, int n) {
        return this.flatMap(function, bl, n, Observable.bufferSize());
    }

    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> function, boolean bl, int n, int n2) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(n, "maxConcurrency");
        ObjectHelper.verifyPositive(n2, "bufferSize");
        if (this instanceof ScalarCallable) {
            Object t = ((ScalarCallable)((Object)this)).call();
            if (t == null) {
                return Observable.empty();
            }
            return ObservableScalarXMap.scalarXMap(t, function);
        }
        return RxJavaPlugins.onAssembly(new ObservableFlatMap(this, function, bl, n, n2));
    }

    public final <U> Observable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, function));
    }

    public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> function) {
        return this.flatMapSingle(function, false);
    }

    public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> function, boolean bl) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableFlatMapSingle(this, function, bl));
    }

    public final Completable ignoreElements() {
        return RxJavaPlugins.onAssembly(new ObservableIgnoreElementsCompletable(this));
    }

    public final Single<Boolean> isEmpty() {
        return this.all(Functions.alwaysFalse());
    }

    public final <R> Observable<R> map(Function<? super T, ? extends R> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new ObservableMap<T, R>(this, function));
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        return this.observeOn(scheduler, false, Observable.bufferSize());
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean bl, int n) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(n, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableObserveOn(this, scheduler, bl, n));
    }

    public final Observable<T> retry() {
        return this.retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    public final Observable<T> retry(long l, Predicate<? super Throwable> predicate) {
        if (l < 0L) {
            throw new IllegalArgumentException("times >= 0 required but it was " + l);
        }
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new ObservableRetryPredicate(this, l, predicate));
    }

    public final Maybe<T> singleElement() {
        return RxJavaPlugins.onAssembly(new ObservableSingleMaybe(this));
    }

    public final Single<T> singleOrError() {
        return RxJavaPlugins.onAssembly(new ObservableSingleSingle<Object>(this, null));
    }

    public final Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        return this.subscribe(consumer, consumer2, Functions.EMPTY_ACTION, Functions.emptyConsumer());
    }

    public final Disposable subscribe(Consumer<? super T> object, Consumer<? super Throwable> consumer, Action action, Consumer<? super Disposable> consumer2) {
        ObjectHelper.requireNonNull(object, "onNext is null");
        ObjectHelper.requireNonNull(consumer, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(consumer2, "onSubscribe is null");
        object = new LambdaObserver<T>((Consumer<? super T>)object, (Consumer<Throwable>)consumer, action, (Consumer<Disposable>)consumer2);
        this.subscribe((Observer<? super T>)object);
        return object;
    }

    @Override
    public final void subscribe(Observer<? super T> observer) {
        ObjectHelper.requireNonNull(observer, "observer is null");
        try {
            observer = RxJavaPlugins.onSubscribe(this, observer);
            ObjectHelper.requireNonNull(observer, "Plugin returned null Observer");
            this.subscribeActual(observer);
            return;
        }
        catch (NullPointerException nullPointerException) {
            throw nullPointerException;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            RxJavaPlugins.onError(throwable);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(throwable);
            throw nullPointerException;
        }
    }

    protected abstract void subscribeActual(Observer<? super T> var1);

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
    }

    public final <E extends Observer<? super T>> E subscribeWith(E e) {
        this.subscribe(e);
        return e;
    }

    public final Flowable<T> toFlowable(BackpressureStrategy backpressureStrategy) {
        FlowableFromObservable<T> flowableFromObservable;
        Flowable flowable = flowableFromObservable = new FlowableFromObservable<T>(this);
        switch (1.$SwitchMap$io$reactivex$BackpressureStrategy[backpressureStrategy.ordinal()]) {
            default: {
                flowable = flowableFromObservable.onBackpressureBuffer();
            }
            case 3: {
                return flowable;
            }
            case 1: {
                return flowableFromObservable.onBackpressureDrop();
            }
            case 2: {
                return flowableFromObservable.onBackpressureLatest();
            }
            case 4: 
        }
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError<T>(flowableFromObservable));
    }

    public final Single<List<T>> toList() {
        return this.toList(16);
    }

    public final Single<List<T>> toList(int n) {
        ObjectHelper.verifyPositive(n, "capacityHint");
        return RxJavaPlugins.onAssembly(new ObservableToListSingle<T, U>(this, n));
    }

}

