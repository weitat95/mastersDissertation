/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.functions;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class Functions {
    static final Predicate<Object> ALWAYS_FALSE;
    static final Predicate<Object> ALWAYS_TRUE;
    public static final Action EMPTY_ACTION;
    static final Consumer<Object> EMPTY_CONSUMER;
    public static final LongConsumer EMPTY_LONG_CONSUMER;
    public static final Runnable EMPTY_RUNNABLE;
    public static final Consumer<Throwable> ERROR_CONSUMER;
    static final Function<Object, Object> IDENTITY;
    static final Comparator<Object> NATURAL_COMPARATOR;
    static final Callable<Object> NULL_SUPPLIER;
    public static final Consumer<Throwable> ON_ERROR_MISSING;
    public static final Consumer<Subscription> REQUEST_MAX;

    static {
        IDENTITY = new Identity();
        EMPTY_RUNNABLE = new EmptyRunnable();
        EMPTY_ACTION = new EmptyAction();
        EMPTY_CONSUMER = new EmptyConsumer();
        ERROR_CONSUMER = new ErrorConsumer();
        ON_ERROR_MISSING = new OnErrorMissingConsumer();
        EMPTY_LONG_CONSUMER = new EmptyLongConsumer();
        ALWAYS_TRUE = new TruePredicate();
        ALWAYS_FALSE = new FalsePredicate();
        NULL_SUPPLIER = new NullCallable();
        NATURAL_COMPARATOR = new NaturalObjectComparator();
        REQUEST_MAX = new MaxRequestSubscription();
    }

    public static <T> Consumer<T> actionConsumer(Action action) {
        return new ActionConsumer(action);
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ALWAYS_FALSE;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    public static <T> Callable<List<T>> createArrayList(int n) {
        return new ArrayListCapacityCallable(n);
    }

    public static <T> Callable<Set<T>> createHashSet() {
        return HashSetCallable.INSTANCE;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return EMPTY_CONSUMER;
    }

    public static <T> Function<T, T> identity() {
        return IDENTITY;
    }

    public static <T> Callable<T> justCallable(T t) {
        return new JustValue(t);
    }

    public static <T1, T2, R> Function<Object[], R> toFunction(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "f is null");
        return new Array2Func<T1, T2, R>(biFunction);
    }

    public static <T1, T2, T3, R> Function<Object[], R> toFunction(Function3<T1, T2, T3, R> function3) {
        ObjectHelper.requireNonNull(function3, "f is null");
        return new Array3Func<T1, T2, T3, R>(function3);
    }

    static final class ActionConsumer<T>
    implements Consumer<T> {
        final Action action;

        ActionConsumer(Action action) {
            this.action = action;
        }

        @Override
        public void accept(T t) throws Exception {
            this.action.run();
        }
    }

    static final class Array2Func<T1, T2, R>
    implements Function<Object[], R> {
        final BiFunction<? super T1, ? super T2, ? extends R> f;

        Array2Func(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
            this.f = biFunction;
        }

        @Override
        public R apply(Object[] arrobject) throws Exception {
            if (arrobject.length != 2) {
                throw new IllegalArgumentException("Array of size 2 expected but got " + arrobject.length);
            }
            return this.f.apply(arrobject[0], arrobject[1]);
        }
    }

    static final class Array3Func<T1, T2, T3, R>
    implements Function<Object[], R> {
        final Function3<T1, T2, T3, R> f;

        Array3Func(Function3<T1, T2, T3, R> function3) {
            this.f = function3;
        }

        @Override
        public R apply(Object[] arrobject) throws Exception {
            if (arrobject.length != 3) {
                throw new IllegalArgumentException("Array of size 3 expected but got " + arrobject.length);
            }
            return this.f.apply(arrobject[0], arrobject[1], arrobject[2]);
        }
    }

    static final class ArrayListCapacityCallable<T>
    implements Callable<List<T>> {
        final int capacity;

        ArrayListCapacityCallable(int n) {
            this.capacity = n;
        }

        @Override
        public List<T> call() throws Exception {
            return new ArrayList(this.capacity);
        }
    }

    static final class EmptyAction
    implements Action {
        EmptyAction() {
        }

        @Override
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }
    }

    static final class EmptyConsumer
    implements Consumer<Object> {
        EmptyConsumer() {
        }

        @Override
        public void accept(Object object) {
        }

        public String toString() {
            return "EmptyConsumer";
        }
    }

    static final class EmptyLongConsumer
    implements LongConsumer {
        EmptyLongConsumer() {
        }
    }

    static final class EmptyRunnable
    implements Runnable {
        EmptyRunnable() {
        }

        @Override
        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }
    }

    static final class ErrorConsumer
    implements Consumer<Throwable> {
        ErrorConsumer() {
        }

        @Override
        public void accept(Throwable throwable) {
            RxJavaPlugins.onError(throwable);
        }
    }

    static final class FalsePredicate
    implements Predicate<Object> {
        FalsePredicate() {
        }

        @Override
        public boolean test(Object object) {
            return false;
        }
    }

    static enum HashSetCallable implements Callable<Set<Object>>
    {
        INSTANCE;


        @Override
        public Set<Object> call() throws Exception {
            return new HashSet<Object>();
        }
    }

    static final class Identity
    implements Function<Object, Object> {
        Identity() {
        }

        @Override
        public Object apply(Object object) {
            return object;
        }

        public String toString() {
            return "IdentityFunction";
        }
    }

    static final class JustValue<T, U>
    implements Function<T, U>,
    Callable<U> {
        final U value;

        JustValue(U u) {
            this.value = u;
        }

        @Override
        public U apply(T t) throws Exception {
            return this.value;
        }

        @Override
        public U call() throws Exception {
            return this.value;
        }
    }

    static final class MaxRequestSubscription
    implements Consumer<Subscription> {
        MaxRequestSubscription() {
        }

        @Override
        public void accept(Subscription subscription) throws Exception {
            subscription.request(Long.MAX_VALUE);
        }
    }

    static final class NaturalObjectComparator
    implements Comparator<Object> {
        NaturalObjectComparator() {
        }

        @Override
        public int compare(Object object, Object object2) {
            return ((Comparable)object).compareTo(object2);
        }
    }

    static final class NullCallable
    implements Callable<Object> {
        NullCallable() {
        }

        @Override
        public Object call() {
            return null;
        }
    }

    static final class OnErrorMissingConsumer
    implements Consumer<Throwable> {
        OnErrorMissingConsumer() {
        }

        @Override
        public void accept(Throwable throwable) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(throwable));
        }
    }

    static final class TruePredicate
    implements Predicate<Object> {
        TruePredicate() {
        }

        @Override
        public boolean test(Object object) {
            return true;
        }
    }

}

