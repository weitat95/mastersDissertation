/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScalarXMap {
    public static <T, U> Flowable<U> scalarXMap(T t, Function<? super T, ? extends Publisher<? extends U>> function) {
        return RxJavaPlugins.onAssembly(new ScalarXMapFlowable((T)t, function));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T, R> boolean tryScalarXMapSubscribe(Publisher<T> publisher, Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function) {
        void var1_7;
        Publisher publisher2;
        Object v;
        block9: {
            if (!(publisher instanceof Callable)) {
                return false;
            }
            try {
                v = ((Callable)((Object)publisher)).call();
                if (v != null) break block9;
            }
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptySubscription.error(throwable, var1_7);
                return true;
            }
            EmptySubscription.complete(var1_7);
            return true;
        }
        try {
            void var2_8;
            publisher2 = (Publisher)ObjectHelper.requireNonNull(var2_8.apply(v), "The mapper returned a null Publisher");
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptySubscription.error(throwable, var1_7);
            return true;
        }
        if (!(publisher2 instanceof Callable)) {
            publisher2.subscribe(var1_7);
            return true;
        }
        try {}
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptySubscription.error(throwable, var1_7);
            return true;
        }
        Object v2 = ((Callable)((Object)publisher2)).call();
        if (v2 == null) {
            EmptySubscription.complete(var1_7);
            return true;
        }
        var1_7.onSubscribe(new ScalarSubscription(var1_7, v2));
        return true;
    }

    static final class ScalarXMapFlowable<T, R>
    extends Flowable<R> {
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final T value;

        ScalarXMapFlowable(T t, Function<? super T, ? extends Publisher<? extends R>> function) {
            this.value = t;
            this.mapper = function;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void subscribeActual(Subscriber<? super R> subscriber) {
            Publisher<R> publisher;
            try {
                publisher = ObjectHelper.requireNonNull(this.mapper.apply(this.value), "The mapper returned a null Publisher");
            }
            catch (Throwable throwable) {
                EmptySubscription.error(throwable, subscriber);
                return;
            }
            if (!(publisher instanceof Callable)) {
                publisher.subscribe(subscriber);
                return;
            }
            try {}
            catch (Throwable throwable) {
                Exceptions.throwIfFatal(throwable);
                EmptySubscription.error(throwable, subscriber);
                return;
            }
            Object v = ((Callable)((Object)publisher)).call();
            if (v == null) {
                EmptySubscription.complete(subscriber);
                return;
            }
            subscriber.onSubscribe(new ScalarSubscription<R>(subscriber, v));
        }
    }

}

