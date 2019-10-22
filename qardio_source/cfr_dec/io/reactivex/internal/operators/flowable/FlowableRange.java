/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRange
extends Flowable<Integer> {
    final int end;
    final int start;

    public FlowableRange(int n, int n2) {
        this.start = n;
        this.end = n + n2;
    }

    @Override
    public void subscribeActual(Subscriber<? super Integer> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber)subscriber, this.start, this.end));
            return;
        }
        subscriber.onSubscribe(new RangeSubscription(subscriber, this.start, this.end));
    }

    static abstract class BaseRangeSubscription
    extends BasicQueueSubscription<Integer> {
        volatile boolean cancelled;
        final int end;
        int index;

        BaseRangeSubscription(int n, int n2) {
            this.index = n;
            this.end = n2;
        }

        @Override
        public final void cancel() {
            this.cancelled = true;
        }

        @Override
        public final void clear() {
            this.index = this.end;
        }

        abstract void fastPath();

        @Override
        public final boolean isEmpty() {
            return this.index == this.end;
        }

        @Override
        public final Integer poll() {
            int n = this.index;
            if (n == this.end) {
                return null;
            }
            this.index = n + 1;
            return n;
        }

        @Override
        public final void request(long l) {
            block3: {
                block2: {
                    if (!SubscriptionHelper.validate(l) || BackpressureHelper.add(this, l) != 0L) break block2;
                    if (l != Long.MAX_VALUE) break block3;
                    this.fastPath();
                }
                return;
            }
            this.slowPath(l);
        }

        @Override
        public final int requestFusion(int n) {
            return n & 1;
        }

        abstract void slowPath(long var1);
    }

    static final class RangeConditionalSubscription
    extends BaseRangeSubscription {
        final ConditionalSubscriber<? super Integer> actual;

        RangeConditionalSubscription(ConditionalSubscriber<? super Integer> conditionalSubscriber, int n, int n2) {
            super(n, n2);
            this.actual = conditionalSubscriber;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void fastPath() {
            int n = this.end;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            for (int i = this.index; i != n; ++i) {
                if (this.cancelled) return;
                {
                    conditionalSubscriber.tryOnNext((Integer)i);
                    continue;
                }
            }
            if (this.cancelled) {
                return;
            }
            conditionalSubscriber.onComplete();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void slowPath(long l) {
            long l2 = 0L;
            int n = this.end;
            int n2 = this.index;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            do {
                long l3;
                if (l2 != l && n2 != n) {
                    if (this.cancelled) return;
                    {
                        l3 = l2;
                        if (conditionalSubscriber.tryOnNext((Integer)n2)) {
                            l3 = l2 + 1L;
                        }
                        ++n2;
                        l2 = l3;
                        continue;
                    }
                }
                if (n2 == n) {
                    if (this.cancelled) return;
                    {
                        conditionalSubscriber.onComplete();
                        return;
                    }
                }
                l = l3 = this.get();
                if (l2 != l3) continue;
                this.index = n2;
                l = this.addAndGet(-l2);
                if (l == 0L) {
                    return;
                }
                l2 = 0L;
            } while (true);
        }
    }

    static final class RangeSubscription
    extends BaseRangeSubscription {
        final Subscriber<? super Integer> actual;

        RangeSubscription(Subscriber<? super Integer> subscriber, int n, int n2) {
            super(n, n2);
            this.actual = subscriber;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void fastPath() {
            int n = this.end;
            Subscriber<? super Integer> subscriber = this.actual;
            for (int i = this.index; i != n; ++i) {
                if (this.cancelled) return;
                {
                    subscriber.onNext((Integer)i);
                    continue;
                }
            }
            if (this.cancelled) {
                return;
            }
            subscriber.onComplete();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void slowPath(long l) {
            long l2 = 0L;
            int n = this.end;
            int n2 = this.index;
            Subscriber<? super Integer> subscriber = this.actual;
            do {
                long l3;
                if (l2 != l && n2 != n) {
                    if (this.cancelled) return;
                    {
                        subscriber.onNext((Integer)n2);
                        ++l2;
                        ++n2;
                        continue;
                    }
                }
                if (n2 == n) {
                    if (this.cancelled) return;
                    {
                        subscriber.onComplete();
                        return;
                    }
                }
                l = l3 = this.get();
                if (l2 != l3) continue;
                this.index = n2;
                l = this.addAndGet(-l2);
                if (l == 0L) {
                    return;
                }
                l2 = 0L;
            } while (true);
        }
    }

}

