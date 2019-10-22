/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatWhen<T>
extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Object>, ? extends Publisher<?>> handler;

    @Override
    public void subscribeActual(Subscriber<? super T> subscriber) {
        FlowableSubscriber<? super T> flowableSubscriber = new SerializedSubscriber<T>(subscriber);
        FlowableProcessor<Object> flowableProcessor = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher<?> publisher = ObjectHelper.requireNonNull(this.handler.apply(flowableProcessor), "handler returned a null Publisher");
            WhenReceiver whenReceiver = new WhenReceiver(this.source);
            flowableSubscriber = new RepeatWhenSubscriber<T>(flowableSubscriber, flowableProcessor, whenReceiver);
            whenReceiver.subscriber = flowableSubscriber;
            subscriber.onSubscribe((Subscription)((Object)flowableSubscriber));
            publisher.subscribe(whenReceiver);
            whenReceiver.onNext(0);
            return;
        }
        catch (Throwable throwable) {
            Exceptions.throwIfFatal(throwable);
            EmptySubscription.error(throwable, subscriber);
            return;
        }
    }

    static final class RepeatWhenSubscriber<T>
    extends WhenSourceSubscriber<T, Object> {
        RepeatWhenSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<Object> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        @Override
        public void onComplete() {
            this.again(0);
        }

        @Override
        public void onError(Throwable throwable) {
            this.receiver.cancel();
            this.actual.onError(throwable);
        }
    }

    static final class WhenReceiver<T, U>
    extends AtomicInteger
    implements FlowableSubscriber<Object>,
    Subscription {
        final AtomicLong requested;
        final Publisher<T> source;
        WhenSourceSubscriber<T, U> subscriber;
        final AtomicReference<Subscription> subscription;

        WhenReceiver(Publisher<T> publisher) {
            this.source = publisher;
            this.subscription = new AtomicReference();
            this.requested = new AtomicLong();
        }

        @Override
        public void cancel() {
            SubscriptionHelper.cancel(this.subscription);
        }

        @Override
        public void onComplete() {
            this.subscriber.cancel();
            this.subscriber.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.subscriber.cancel();
            this.subscriber.actual.onError(throwable);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onNext(Object object) {
            if (this.getAndIncrement() != 0) return;
            do {
                if (SubscriptionHelper.isCancelled(this.subscription.get())) {
                    return;
                }
                this.source.subscribe(this.subscriber);
            } while (this.decrementAndGet() != 0);
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.subscription, this.requested, subscription);
        }

        @Override
        public void request(long l) {
            SubscriptionHelper.deferredRequest(this.subscription, this.requested, l);
        }
    }

    static abstract class WhenSourceSubscriber<T, U>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T> {
        protected final Subscriber<? super T> actual;
        protected final FlowableProcessor<U> processor;
        private long produced;
        protected final Subscription receiver;

        WhenSourceSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<U> flowableProcessor, Subscription subscription) {
            this.actual = subscriber;
            this.processor = flowableProcessor;
            this.receiver = subscription;
        }

        protected final void again(U u) {
            long l = this.produced;
            if (l != 0L) {
                this.produced = 0L;
                this.produced(l);
            }
            this.receiver.request(1L);
            this.processor.onNext(u);
        }

        @Override
        public final void cancel() {
            super.cancel();
            this.receiver.cancel();
        }

        @Override
        public final void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        @Override
        public final void onSubscribe(Subscription subscription) {
            this.setSubscription(subscription);
        }
    }

}

