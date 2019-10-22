/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.plugins;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class RxJavaPlugins {
    static volatile Consumer<? super Throwable> errorHandler;
    static volatile boolean failNonBlockingScheduler;
    static volatile BooleanSupplier onBeforeBlocking;
    static volatile Function<? super Completable, ? extends Completable> onCompletableAssembly;
    static volatile BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> onCompletableSubscribe;
    static volatile Function<? super Scheduler, ? extends Scheduler> onComputationHandler;
    static volatile Function<? super Flowable, ? extends Flowable> onFlowableAssembly;
    static volatile BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> onFlowableSubscribe;
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitComputationHandler;
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitIoHandler;
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitNewThreadHandler;
    static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitSingleHandler;
    static volatile Function<? super Scheduler, ? extends Scheduler> onIoHandler;
    static volatile Function<? super Maybe, ? extends Maybe> onMaybeAssembly;
    static volatile BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> onMaybeSubscribe;
    static volatile Function<? super Observable, ? extends Observable> onObservableAssembly;
    static volatile BiFunction<? super Observable, ? super Observer, ? extends Observer> onObservableSubscribe;
    static volatile Function<? super Runnable, ? extends Runnable> onScheduleHandler;
    static volatile Function<? super Single, ? extends Single> onSingleAssembly;
    static volatile BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> onSingleSubscribe;

    static <T, U, R> R apply(BiFunction<T, U, R> biFunction, T t, U u) {
        try {
            biFunction = biFunction.apply(t, u);
        }
        catch (Throwable throwable) {
            throw ExceptionHelper.wrapOrThrow(throwable);
        }
        return (R)biFunction;
    }

    static <T, R> R apply(Function<T, R> function, T t) {
        try {
            function = function.apply(t);
        }
        catch (Throwable throwable) {
            throw ExceptionHelper.wrapOrThrow(throwable);
        }
        return (R)function;
    }

    static Scheduler applyRequireNonNull(Function<? super Callable<Scheduler>, ? extends Scheduler> function, Callable<Scheduler> callable) {
        return ObjectHelper.requireNonNull(RxJavaPlugins.apply(function, callable), "Scheduler Callable result can't be null");
    }

    static Scheduler callRequireNonNull(Callable<Scheduler> object) {
        try {
            object = ObjectHelper.requireNonNull(object.call(), "Scheduler Callable result can't be null");
            return object;
        }
        catch (Throwable throwable) {
            throw ExceptionHelper.wrapOrThrow(throwable);
        }
    }

    public static Scheduler initComputationScheduler(Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = onInitComputationHandler;
        if (function == null) {
            return RxJavaPlugins.callRequireNonNull(callable);
        }
        return RxJavaPlugins.applyRequireNonNull(function, callable);
    }

    public static Scheduler initIoScheduler(Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = onInitIoHandler;
        if (function == null) {
            return RxJavaPlugins.callRequireNonNull(callable);
        }
        return RxJavaPlugins.applyRequireNonNull(function, callable);
    }

    public static Scheduler initNewThreadScheduler(Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = onInitNewThreadHandler;
        if (function == null) {
            return RxJavaPlugins.callRequireNonNull(callable);
        }
        return RxJavaPlugins.applyRequireNonNull(function, callable);
    }

    public static Scheduler initSingleScheduler(Callable<Scheduler> callable) {
        ObjectHelper.requireNonNull(callable, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> function = onInitSingleHandler;
        if (function == null) {
            return RxJavaPlugins.callRequireNonNull(callable);
        }
        return RxJavaPlugins.applyRequireNonNull(function, callable);
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isBug(Throwable throwable) {
        return throwable instanceof OnErrorNotImplementedException || throwable instanceof MissingBackpressureException || throwable instanceof IllegalStateException || throwable instanceof NullPointerException || throwable instanceof IllegalArgumentException || throwable instanceof CompositeException;
    }

    public static boolean isFailOnNonBlockingScheduler() {
        return failNonBlockingScheduler;
    }

    public static Completable onAssembly(Completable completable) {
        Function<? super Completable, ? extends Completable> function = onCompletableAssembly;
        if (function != null) {
            return RxJavaPlugins.apply(function, completable);
        }
        return completable;
    }

    public static <T> Flowable<T> onAssembly(Flowable<T> flowable) {
        Function<? super Flowable, ? extends Flowable> function = onFlowableAssembly;
        if (function != null) {
            return RxJavaPlugins.apply(function, flowable);
        }
        return flowable;
    }

    public static <T> Maybe<T> onAssembly(Maybe<T> maybe) {
        Function<? super Maybe, ? extends Maybe> function = onMaybeAssembly;
        if (function != null) {
            return RxJavaPlugins.apply(function, maybe);
        }
        return maybe;
    }

    public static <T> Observable<T> onAssembly(Observable<T> observable) {
        Function<? super Observable, ? extends Observable> function = onObservableAssembly;
        if (function != null) {
            return RxJavaPlugins.apply(function, observable);
        }
        return observable;
    }

    public static <T> Single<T> onAssembly(Single<T> single) {
        Function<? super Single, ? extends Single> function = onSingleAssembly;
        if (function != null) {
            return RxJavaPlugins.apply(function, single);
        }
        return single;
    }

    public static boolean onBeforeBlocking() {
        BooleanSupplier booleanSupplier = onBeforeBlocking;
        if (booleanSupplier != null) {
            try {
                boolean bl = booleanSupplier.getAsBoolean();
                return bl;
            }
            catch (Throwable throwable) {
                throw ExceptionHelper.wrapOrThrow(throwable);
            }
        }
        return false;
    }

    public static Scheduler onComputationScheduler(Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onComputationHandler;
        if (function == null) {
            return scheduler;
        }
        return RxJavaPlugins.apply(function, scheduler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void onError(Throwable throwable) {
        Throwable throwable2;
        Consumer<? super Throwable> consumer = errorHandler;
        if (throwable == null) {
            throwable2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        } else {
            throwable2 = throwable;
            if (!RxJavaPlugins.isBug(throwable)) {
                throwable2 = new UndeliverableException(throwable);
            }
        }
        if (consumer != null) {
            try {
                consumer.accept(throwable2);
                return;
            }
            catch (Throwable throwable3) {
                throwable3.printStackTrace();
                RxJavaPlugins.uncaught(throwable3);
            }
        }
        throwable2.printStackTrace();
        RxJavaPlugins.uncaught(throwable2);
    }

    public static Scheduler onIoScheduler(Scheduler scheduler) {
        Function<? super Scheduler, ? extends Scheduler> function = onIoHandler;
        if (function == null) {
            return scheduler;
        }
        return RxJavaPlugins.apply(function, scheduler);
    }

    public static Runnable onSchedule(Runnable runnable) {
        Function<? super Runnable, ? extends Runnable> function = onScheduleHandler;
        if (function == null) {
            return runnable;
        }
        return RxJavaPlugins.apply(function, runnable);
    }

    public static CompletableObserver onSubscribe(Completable completable, CompletableObserver completableObserver) {
        BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> biFunction = onCompletableSubscribe;
        if (biFunction != null) {
            return RxJavaPlugins.apply(biFunction, completable, completableObserver);
        }
        return completableObserver;
    }

    public static <T> MaybeObserver<? super T> onSubscribe(Maybe<T> maybe, MaybeObserver<? super T> maybeObserver) {
        BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> biFunction = onMaybeSubscribe;
        if (biFunction != null) {
            return RxJavaPlugins.apply(biFunction, maybe, maybeObserver);
        }
        return maybeObserver;
    }

    public static <T> Observer<? super T> onSubscribe(Observable<T> observable, Observer<? super T> observer) {
        BiFunction<? super Observable, ? super Observer, ? extends Observer> biFunction = onObservableSubscribe;
        if (biFunction != null) {
            return RxJavaPlugins.apply(biFunction, observable, observer);
        }
        return observer;
    }

    public static <T> SingleObserver<? super T> onSubscribe(Single<T> single, SingleObserver<? super T> singleObserver) {
        BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> biFunction = onSingleSubscribe;
        if (biFunction != null) {
            return RxJavaPlugins.apply(biFunction, single, singleObserver);
        }
        return singleObserver;
    }

    public static <T> Subscriber<? super T> onSubscribe(Flowable<T> flowable, Subscriber<? super T> subscriber) {
        BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> biFunction = onFlowableSubscribe;
        if (biFunction != null) {
            return RxJavaPlugins.apply(biFunction, flowable, subscriber);
        }
        return subscriber;
    }

    static void uncaught(Throwable throwable) {
        Thread thread = Thread.currentThread();
        thread.getUncaughtExceptionHandler().uncaughtException(thread, throwable);
    }
}

