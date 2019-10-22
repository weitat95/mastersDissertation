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
import io.reactivex.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryWhen<T>
extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;

    public FlowableRetryWhen(Flowable<T> flowable, Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        super(flowable);
        this.handler = function;
    }

    @Override
    public void subscribeActual(Subscriber<? super T> subscriber) {
        FlowableSubscriber<? super T> flowableSubscriber = new SerializedSubscriber<T>(subscriber);
        FlowableProcessor<Throwable> flowableProcessor = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher<?> publisher = ObjectHelper.requireNonNull(this.handler.apply(flowableProcessor), "handler returned a null Publisher");
            FlowableRepeatWhen.WhenReceiver whenReceiver = new FlowableRepeatWhen.WhenReceiver(this.source);
            flowableSubscriber = new RetryWhenSubscriber<T>(flowableSubscriber, flowableProcessor, whenReceiver);
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

    static final class RetryWhenSubscriber<T>
    extends FlowableRepeatWhen.WhenSourceSubscriber<T, Throwable> {
        RetryWhenSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<Throwable> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        @Override
        public void onComplete() {
            this.receiver.cancel();
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            this.again(throwable);
        }
    }

}

