/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.operators.flowable.AbstractFlowableWithUpstream;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableMap<T, U>
extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends U> mapper;

    public FlowableMap(Flowable<T> flowable, Function<? super T, ? extends U> function) {
        super(flowable);
        this.mapper = function;
    }

    @Override
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe(new MapConditionalSubscriber<T, U>((ConditionalSubscriber)subscriber, this.mapper));
            return;
        }
        this.source.subscribe(new MapSubscriber<T, U>(subscriber, this.mapper));
    }

    static final class MapConditionalSubscriber<T, U>
    extends BasicFuseableConditionalSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapConditionalSubscriber(ConditionalSubscriber<? super U> conditionalSubscriber, Function<? super T, ? extends U> function) {
            super(conditionalSubscriber);
            this.mapper = function;
        }

        @Override
        public void onNext(T object) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(null);
                return;
            }
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper function returned a null value.");
            }
            catch (Throwable throwable) {
                this.fail(throwable);
                return;
            }
            this.actual.onNext(object);
        }

        @Override
        public U poll() throws Exception {
            Object t = this.qs.poll();
            if (t != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }

        @Override
        public int requestFusion(int n) {
            return this.transitiveBoundaryFusion(n);
        }

        @Override
        public boolean tryOnNext(T object) {
            if (this.done) {
                return false;
            }
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper function returned a null value.");
                return this.actual.tryOnNext(object);
            }
            catch (Throwable throwable) {
                this.fail(throwable);
                return true;
            }
        }
    }

    static final class MapSubscriber<T, U>
    extends BasicFuseableSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends U> function) {
            super(subscriber);
            this.mapper = function;
        }

        @Override
        public void onNext(T object) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(null);
                return;
            }
            try {
                object = ObjectHelper.requireNonNull(this.mapper.apply(object), "The mapper function returned a null value.");
                this.actual.onNext(object);
                return;
            }
            catch (Throwable throwable) {
                this.fail(throwable);
                return;
            }
        }

        @Override
        public U poll() throws Exception {
            Object t = this.qs.poll();
            if (t != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value.");
            }
            return null;
        }

        @Override
        public int requestFusion(int n) {
            return this.transitiveBoundaryFusion(n);
        }
    }

}

