/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.internal.operators.single.SingleCreate;
import io.reactivex.internal.operators.single.SingleDoOnError;
import io.reactivex.internal.operators.single.SingleError;
import io.reactivex.internal.operators.single.SingleFlatMap;
import io.reactivex.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.internal.operators.single.SingleFromCallable;
import io.reactivex.internal.operators.single.SingleFromUnsafeSource;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.internal.operators.single.SingleObserveOn;
import io.reactivex.internal.operators.single.SingleOnErrorReturn;
import io.reactivex.internal.operators.single.SingleResumeNext;
import io.reactivex.internal.operators.single.SingleSubscribeOn;
import io.reactivex.internal.operators.single.SingleToFlowable;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.internal.operators.single.SingleZipArray;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public abstract class Single<T>
implements SingleSource<T> {
    public static <T> Single<T> create(SingleOnSubscribe<T> singleOnSubscribe) {
        ObjectHelper.requireNonNull(singleOnSubscribe, "source is null");
        return RxJavaPlugins.onAssembly(new SingleCreate<T>(singleOnSubscribe));
    }

    public static <T> Single<T> error(Throwable throwable) {
        ObjectHelper.requireNonNull(throwable, "error is null");
        return Single.error(Functions.justCallable(throwable));
    }

    public static <T> Single<T> error(Callable<? extends Throwable> callable) {
        ObjectHelper.requireNonNull(callable, "errorSupplier is null");
        return RxJavaPlugins.onAssembly(new SingleError(callable));
    }

    public static <T> Single<T> fromCallable(Callable<? extends T> callable) {
        ObjectHelper.requireNonNull(callable, "callable is null");
        return RxJavaPlugins.onAssembly(new SingleFromCallable<T>(callable));
    }

    public static <T> Single<T> just(T t) {
        ObjectHelper.requireNonNull(t, "value is null");
        return RxJavaPlugins.onAssembly(new SingleJust<T>(t));
    }

    private static <T> Single<T> toSingle(Flowable<T> flowable) {
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle<Object>(flowable, null));
    }

    public static <T> Single<T> wrap(SingleSource<T> singleSource) {
        ObjectHelper.requireNonNull(singleSource, "source is null");
        if (singleSource instanceof Single) {
            return RxJavaPlugins.onAssembly((Single)singleSource);
        }
        return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource<T>(singleSource));
    }

    public static <T1, T2, T3, R> Single<R> zip(SingleSource<? extends T1> singleSource, SingleSource<? extends T2> singleSource2, SingleSource<? extends T3> singleSource3, Function3<? super T1, ? super T2, ? super T3, ? extends R> function3) {
        ObjectHelper.requireNonNull(singleSource, "source1 is null");
        ObjectHelper.requireNonNull(singleSource2, "source2 is null");
        ObjectHelper.requireNonNull(singleSource3, "source3 is null");
        return Single.zipArray(Functions.toFunction(function3), singleSource, singleSource2, singleSource3);
    }

    public static <T1, T2, R> Single<R> zip(SingleSource<? extends T1> singleSource, SingleSource<? extends T2> singleSource2, BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(singleSource, "source1 is null");
        ObjectHelper.requireNonNull(singleSource2, "source2 is null");
        return Single.zipArray(Functions.toFunction(biFunction), singleSource, singleSource2);
    }

    public static <T, R> Single<R> zipArray(Function<? super Object[], ? extends R> function, SingleSource<? extends T> ... arrsingleSource) {
        ObjectHelper.requireNonNull(function, "zipper is null");
        ObjectHelper.requireNonNull(arrsingleSource, "sources is null");
        if (arrsingleSource.length == 0) {
            return Single.error(new NoSuchElementException());
        }
        return RxJavaPlugins.onAssembly(new SingleZipArray<T, R>(arrsingleSource, function));
    }

    public final T blockingGet() {
        BlockingMultiObserver blockingMultiObserver = new BlockingMultiObserver();
        this.subscribe(blockingMultiObserver);
        return blockingMultiObserver.blockingGet();
    }

    public final <R> Single<R> compose(SingleTransformer<? super T, ? extends R> singleTransformer) {
        return Single.wrap(ObjectHelper.requireNonNull(singleTransformer, "transformer is null").apply(this));
    }

    public final Single<T> doOnError(Consumer<? super Throwable> consumer) {
        ObjectHelper.requireNonNull(consumer, "onError is null");
        return RxJavaPlugins.onAssembly(new SingleDoOnError(this, consumer));
    }

    public final <R> Single<R> flatMap(Function<? super T, ? extends SingleSource<? extends R>> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMap(this, function));
    }

    public final <U> Observable<U> flattenAsObservable(Function<? super T, ? extends Iterable<? extends U>> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleFlatMapIterableObservable(this, function));
    }

    public final <R> Single<R> map(Function<? super T, ? extends R> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new SingleMap<T, R>(this, function));
    }

    public final Single<T> observeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleObserveOn(this, scheduler));
    }

    public final Single<T> onErrorResumeNext(Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
        ObjectHelper.requireNonNull(function, "resumeFunctionInCaseOfError is null");
        return RxJavaPlugins.onAssembly(new SingleResumeNext(this, function));
    }

    public final Single<T> onErrorReturn(Function<Throwable, ? extends T> function) {
        ObjectHelper.requireNonNull(function, "resumeFunction is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn<Object>(this, function, null));
    }

    public final Single<T> onErrorReturnItem(T t) {
        ObjectHelper.requireNonNull(t, "value is null");
        return RxJavaPlugins.onAssembly(new SingleOnErrorReturn<T>(this, null, t));
    }

    public final Single<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        return Single.toSingle(this.toFlowable().retryWhen(function));
    }

    public final Disposable subscribe() {
        return this.subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING);
    }

    public final Disposable subscribe(Consumer<? super T> consumer) {
        return this.subscribe(consumer, Functions.ON_ERROR_MISSING);
    }

    public final Disposable subscribe(Consumer<? super T> object, Consumer<? super Throwable> consumer) {
        ObjectHelper.requireNonNull(object, "onSuccess is null");
        ObjectHelper.requireNonNull(consumer, "onError is null");
        object = new ConsumerSingleObserver<T>((Consumer<? super T>)object, (Consumer<Throwable>)consumer);
        this.subscribe((SingleObserver<? super T>)object);
        return object;
    }

    @Override
    public final void subscribe(SingleObserver<? super T> singleObserver) {
        ObjectHelper.requireNonNull(singleObserver, "subscriber is null");
        singleObserver = RxJavaPlugins.onSubscribe(this, singleObserver);
        ObjectHelper.requireNonNull(singleObserver, "subscriber returned by the RxJavaPlugins hook is null");
        try {
            this.subscribeActual(singleObserver);
            return;
        }
        catch (NullPointerException nullPointerException) {
            throw nullPointerException;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            NullPointerException nullPointerException = new NullPointerException("subscribeActual failed");
            nullPointerException.initCause(throwable);
            throw nullPointerException;
        }
    }

    protected abstract void subscribeActual(SingleObserver<? super T> var1);

    public final Single<T> subscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new SingleSubscribeOn<T>(this, scheduler));
    }

    public final <E extends SingleObserver<? super T>> E subscribeWith(E e) {
        this.subscribe(e);
        return e;
    }

    public final Completable toCompletable() {
        return RxJavaPlugins.onAssembly(new CompletableFromSingle<T>(this));
    }

    public final Flowable<T> toFlowable() {
        if (this instanceof FuseToFlowable) {
            return ((FuseToFlowable)((Object)this)).fuseToFlowable();
        }
        return RxJavaPlugins.onAssembly(new SingleToFlowable<T>(this));
    }

    public final Maybe<T> toMaybe() {
        if (this instanceof FuseToMaybe) {
            return ((FuseToMaybe)((Object)this)).fuseToMaybe();
        }
        return RxJavaPlugins.onAssembly(new MaybeFromSingle<T>(this));
    }

    public final Observable<T> toObservable() {
        if (this instanceof FuseToObservable) {
            return ((FuseToObservable)((Object)this)).fuseToObservable();
        }
        return RxJavaPlugins.onAssembly(new SingleToObservable<T>(this));
    }
}

