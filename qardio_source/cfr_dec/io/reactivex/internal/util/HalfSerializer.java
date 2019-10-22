/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class HalfSerializer {
    public static void onComplete(Subscriber<?> subscriber, AtomicInteger serializable, AtomicThrowable atomicThrowable) {
        block3: {
            block2: {
                if (serializable.getAndIncrement() != 0) break block2;
                serializable = atomicThrowable.terminate();
                if (serializable == null) break block3;
                subscriber.onError((Throwable)serializable);
            }
            return;
        }
        subscriber.onComplete();
    }

    public static void onError(Subscriber<?> subscriber, Throwable throwable, AtomicInteger atomicInteger, AtomicThrowable atomicThrowable) {
        if (atomicThrowable.addThrowable(throwable)) {
            if (atomicInteger.getAndIncrement() == 0) {
                subscriber.onError(atomicThrowable.terminate());
            }
            return;
        }
        RxJavaPlugins.onError(throwable);
    }

    public static <T> void onNext(Subscriber<? super T> subscriber, T object, AtomicInteger atomicInteger, AtomicThrowable atomicThrowable) {
        block3: {
            block2: {
                if (atomicInteger.get() != 0 || !atomicInteger.compareAndSet(0, 1)) break block2;
                subscriber.onNext(object);
                if (atomicInteger.decrementAndGet() == 0) break block2;
                object = atomicThrowable.terminate();
                if (object == null) break block3;
                subscriber.onError((Throwable)object);
            }
            return;
        }
        subscriber.onComplete();
    }
}

