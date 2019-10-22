/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimer
extends Flowable<Long> {
    final long delay;
    final Scheduler scheduler;
    final TimeUnit unit;

    public FlowableTimer(long l, TimeUnit timeUnit, Scheduler scheduler) {
        this.delay = l;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

    @Override
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        TimerSubscriber timerSubscriber = new TimerSubscriber(subscriber);
        subscriber.onSubscribe(timerSubscriber);
        timerSubscriber.setResource(this.scheduler.scheduleDirect(timerSubscriber, this.delay, this.unit));
    }

    static final class TimerSubscriber
    extends AtomicReference<Disposable>
    implements Runnable,
    Subscription {
        final Subscriber<? super Long> actual;
        volatile boolean requested;

        TimerSubscriber(Subscriber<? super Long> subscriber) {
            this.actual = subscriber;
        }

        @Override
        public void cancel() {
            DisposableHelper.dispose(this);
        }

        @Override
        public void request(long l) {
            if (SubscriptionHelper.validate(l)) {
                this.requested = true;
            }
        }

        @Override
        public void run() {
            block3: {
                block2: {
                    if (this.get() == DisposableHelper.DISPOSED) break block2;
                    if (!this.requested) break block3;
                    this.actual.onNext((Long)0L);
                    this.lazySet(EmptyDisposable.INSTANCE);
                    this.actual.onComplete();
                }
                return;
            }
            this.lazySet(EmptyDisposable.INSTANCE);
            this.actual.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.trySet(this, disposable);
        }
    }

}

