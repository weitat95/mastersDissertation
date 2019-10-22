/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.MaybeEmpty;
import io.reactivex.internal.operators.maybe.MaybeError;
import io.reactivex.internal.operators.maybe.MaybeFilter;
import io.reactivex.internal.operators.maybe.MaybeFlatten;
import io.reactivex.internal.operators.maybe.MaybeJust;
import io.reactivex.internal.operators.maybe.MaybeMergeArray;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.internal.operators.maybe.MaybeSubscribeOn;
import io.reactivex.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.internal.operators.maybe.MaybeUnsafeCreate;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class Maybe<T>
implements MaybeSource<T> {
    public static <T> Maybe<T> empty() {
        return RxJavaPlugins.onAssembly(MaybeEmpty.INSTANCE);
    }

    public static <T> Maybe<T> error(Throwable throwable) {
        ObjectHelper.requireNonNull(throwable, "exception is null");
        return RxJavaPlugins.onAssembly(new MaybeError(throwable));
    }

    public static <T> Maybe<T> just(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new MaybeJust<T>(t));
    }

    public static <T> Flowable<T> merge(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2) {
        ObjectHelper.requireNonNull(maybeSource, "source1 is null");
        ObjectHelper.requireNonNull(maybeSource2, "source2 is null");
        return Maybe.mergeArray(maybeSource, maybeSource2);
    }

    public static <T> Flowable<T> mergeArray(MaybeSource<? extends T> ... arrmaybeSource) {
        ObjectHelper.requireNonNull(arrmaybeSource, "sources is null");
        if (arrmaybeSource.length == 0) {
            return Flowable.empty();
        }
        if (arrmaybeSource.length == 1) {
            return RxJavaPlugins.onAssembly(new MaybeToFlowable<T>(arrmaybeSource[0]));
        }
        return RxJavaPlugins.onAssembly(new MaybeMergeArray<T>(arrmaybeSource));
    }

    public static <T> Maybe<T> wrap(MaybeSource<T> maybeSource) {
        if (maybeSource instanceof Maybe) {
            return RxJavaPlugins.onAssembly((Maybe)maybeSource);
        }
        ObjectHelper.requireNonNull(maybeSource, "onSubscribe is null");
        return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate<T>(maybeSource));
    }

    public final <R> Maybe<R> compose(MaybeTransformer<? super T, ? extends R> maybeTransformer) {
        return Maybe.wrap(ObjectHelper.requireNonNull(maybeTransformer, "transformer is null").apply(this));
    }

    public final Maybe<T> filter(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new MaybeFilter<T>(this, predicate));
    }

    public final <R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends R>> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new MaybeFlatten(this, function));
    }

    public final Flowable<T> mergeWith(MaybeSource<? extends T> maybeSource) {
        ObjectHelper.requireNonNull(maybeSource, "other is null");
        return Maybe.merge(this, maybeSource);
    }

    public final Maybe<T> observeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeObserveOn(this, scheduler));
    }

    @Override
    public final void subscribe(MaybeObserver<? super T> maybeObserver) {
        ObjectHelper.requireNonNull(maybeObserver, "observer is null");
        maybeObserver = RxJavaPlugins.onSubscribe(this, maybeObserver);
        ObjectHelper.requireNonNull(maybeObserver, "observer returned by the RxJavaPlugins hook is null");
        try {
            this.subscribeActual(maybeObserver);
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

    protected abstract void subscribeActual(MaybeObserver<? super T> var1);

    public final Maybe<T> subscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new MaybeSubscribeOn<T>(this, scheduler));
    }
}

