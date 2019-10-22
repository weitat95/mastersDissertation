/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.flowable.FlowableConcatArray;
import io.reactivex.internal.operators.flowable.FlowableEmpty;
import io.reactivex.internal.operators.flowable.FlowableError;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.internal.operators.flowable.FlowableFromPublisher;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.operators.flowable.FlowableJust;
import io.reactivex.internal.operators.flowable.FlowableMap;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBuffer;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureLatest;
import io.reactivex.internal.operators.flowable.FlowableRange;
import io.reactivex.internal.operators.flowable.FlowableRepeat;
import io.reactivex.internal.operators.flowable.FlowableRetryWhen;
import io.reactivex.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.internal.operators.flowable.FlowableSwitchIfEmpty;
import io.reactivex.internal.operators.flowable.FlowableTake;
import io.reactivex.internal.operators.flowable.FlowableTimer;
import io.reactivex.internal.operators.flowable.FlowableZip;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import io.reactivex.internal.subscribers.StrictSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class Flowable<T>
implements Publisher<T> {
    static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128));

    public static int bufferSize() {
        return BUFFER_SIZE;
    }

    public static <T> Flowable<T> concat(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return Flowable.concatArray(publisher, publisher2);
    }

    public static <T> Flowable<T> concatArray(Publisher<? extends T> ... arrpublisher) {
        if (arrpublisher.length == 0) {
            return Flowable.empty();
        }
        if (arrpublisher.length == 1) {
            return Flowable.fromPublisher(arrpublisher[0]);
        }
        return RxJavaPlugins.onAssembly(new FlowableConcatArray<T>(arrpublisher, false));
    }

    public static <T> Flowable<T> empty() {
        return RxJavaPlugins.onAssembly(FlowableEmpty.INSTANCE);
    }

    public static <T> Flowable<T> error(Throwable throwable) {
        ObjectHelper.requireNonNull(throwable, "throwable is null");
        return Flowable.error(Functions.justCallable(throwable));
    }

    public static <T> Flowable<T> error(Callable<? extends Throwable> callable) {
        ObjectHelper.requireNonNull(callable, "errorSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableError(callable));
    }

    public static <T> Flowable<T> fromPublisher(Publisher<? extends T> publisher) {
        if (publisher instanceof Flowable) {
            return RxJavaPlugins.onAssembly((Flowable)publisher);
        }
        ObjectHelper.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new FlowableFromPublisher<T>(publisher));
    }

    public static <T> Flowable<T> just(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new FlowableJust<T>(t));
    }

    public static Flowable<Integer> range(int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + n2);
        }
        if (n2 == 0) {
            return Flowable.empty();
        }
        if (n2 == 1) {
            return Flowable.just(n);
        }
        if ((long)n + (long)(n2 - 1) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Integer overflow");
        }
        return RxJavaPlugins.onAssembly(new FlowableRange(n, n2));
    }

    public static Flowable<Long> timer(long l, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimer(Math.max(0L, l), timeUnit, scheduler));
    }

    public static <T1, T2, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return Flowable.zipArray(Functions.toFunction(biFunction), false, Flowable.bufferSize(), publisher, publisher2);
    }

    public static <T, R> Flowable<R> zipArray(Function<? super Object[], ? extends R> function, boolean bl, int n, Publisher<? extends T> ... arrpublisher) {
        if (arrpublisher.length == 0) {
            return Flowable.empty();
        }
        ObjectHelper.requireNonNull(function, "zipper is null");
        ObjectHelper.verifyPositive(n, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableZip<T, R>(arrpublisher, null, function, n, bl));
    }

    public final Flowable<T> concatWith(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return Flowable.concat(this, publisher);
    }

    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return this.flatMap(function, false, Flowable.bufferSize(), Flowable.bufferSize());
    }

    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean bl, int n, int n2) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(n, "maxConcurrency");
        ObjectHelper.verifyPositive(n2, "bufferSize");
        if (this instanceof ScalarCallable) {
            Object t = ((ScalarCallable)((Object)this)).call();
            if (t == null) {
                return Flowable.empty();
            }
            return FlowableScalarXMap.scalarXMap(t, function);
        }
        return RxJavaPlugins.onAssembly(new FlowableFlatMap(this, function, bl, n, n2));
    }

    public final <R> Flowable<R> map(Function<? super T, ? extends R> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableMap<T, R>(this, function));
    }

    public final Flowable<T> onBackpressureBuffer() {
        return this.onBackpressureBuffer(Flowable.bufferSize(), false, true);
    }

    public final Flowable<T> onBackpressureBuffer(int n, boolean bl, boolean bl2) {
        ObjectHelper.verifyPositive(n, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, n, bl2, bl, Functions.EMPTY_ACTION));
    }

    public final Flowable<T> onBackpressureDrop() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this));
    }

    public final Flowable<T> onBackpressureLatest() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureLatest(this));
    }

    public final Flowable<T> repeat() {
        return this.repeat(Long.MAX_VALUE);
    }

    public final Flowable<T> repeat(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("times >= 0 required but it was " + l);
        }
        if (l == 0L) {
            return Flowable.empty();
        }
        return RxJavaPlugins.onAssembly(new FlowableRepeat(this, l));
    }

    public final Flowable<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        ObjectHelper.requireNonNull(function, "handler is null");
        return RxJavaPlugins.onAssembly(new FlowableRetryWhen(this, function));
    }

    public final Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        return this.subscribe(consumer, consumer2, Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    public final Disposable subscribe(Consumer<? super T> object, Consumer<? super Throwable> consumer, Action action, Consumer<? super Subscription> consumer2) {
        ObjectHelper.requireNonNull(object, "onNext is null");
        ObjectHelper.requireNonNull(consumer, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(consumer2, "onSubscribe is null");
        object = new LambdaSubscriber<T>((Consumer<? super T>)object, (Consumer<Throwable>)consumer, action, (Consumer<Subscription>)consumer2);
        this.subscribe((FlowableSubscriber<? super T>)object);
        return object;
    }

    @Override
    public final void subscribe(FlowableSubscriber<? super T> subscriber) {
        ObjectHelper.requireNonNull(subscriber, "s is null");
        try {
            subscriber = RxJavaPlugins.onSubscribe(this, subscriber);
            ObjectHelper.requireNonNull(subscriber, "Plugin returned null Subscriber");
            this.subscribeActual(subscriber);
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

    @Override
    public final void subscribe(Subscriber<? super T> subscriber) {
        if (subscriber instanceof FlowableSubscriber) {
            this.subscribe((FlowableSubscriber)subscriber);
            return;
        }
        ObjectHelper.requireNonNull(subscriber, "s is null");
        this.subscribe((FlowableSubscriber<? super T>)new StrictSubscriber<T>(subscriber));
    }

    protected abstract void subscribeActual(Subscriber<? super T> var1);

    public final Flowable<T> switchIfEmpty(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchIfEmpty<T>(this, publisher));
    }

    public final Flowable<T> take(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("count >= 0 required but it was " + l);
        }
        return RxJavaPlugins.onAssembly(new FlowableTake<T>(this, l));
    }

    public final Observable<T> toObservable() {
        return RxJavaPlugins.onAssembly(new ObservableFromPublisher<T>(this));
    }

    public final <U, R> Flowable<R> zipWith(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return Flowable.zip(this, publisher, biFunction);
    }
}

