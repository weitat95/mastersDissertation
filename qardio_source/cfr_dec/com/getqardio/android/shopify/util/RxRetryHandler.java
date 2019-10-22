/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import android.support.v4.util.Pair;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$1;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$2;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$3;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$4;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$5;
import com.getqardio.android.shopify.util.RxRetryHandler$Builder$$Lambda$6;
import com.getqardio.android.shopify.util.Util;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public final class RxRetryHandler {
    private static final long DEFAULT_DELAY = TimeUnit.MILLISECONDS.toMillis(400L);

    private RxRetryHandler() {
    }

    public static Builder delay(long l, TimeUnit timeUnit) {
        return new Builder().delay(l, timeUnit);
    }

    public static Builder exponentialBackoff(long l, TimeUnit timeUnit, float f) {
        return new Builder().exponentialBackoff(l, timeUnit, f);
    }

    public static final class Builder {
        private Flowable<Long> delayPublisher;
        private int maxRetries;
        private Scheduler scheduler;
        private Predicate<? super Throwable> throwablePredicate = RxRetryHandler$Builder$$Lambda$1.lambdaFactory$();

        private Builder() {
            this.scheduler = Schedulers.computation();
            this.delayPublisher = Flowable.just(DEFAULT_DELAY).repeat();
        }

        static /* synthetic */ Long lambda$exponentialBackoff$1(float f, TimeUnit timeUnit, long l, Integer n) throws Exception {
            return Math.round(Math.pow(f, n - 1) * (double)timeUnit.toMillis(l));
        }

        static /* synthetic */ boolean lambda$new$0(Throwable throwable) throws Exception {
            return true;
        }

        public Function<Flowable<? extends Throwable>, Flowable<Object>> build() {
            if (this.maxRetries >= 0) {
                this.delayPublisher = this.delayPublisher.take(this.maxRetries);
            }
            return RxRetryHandler$Builder$$Lambda$3.lambdaFactory$(this);
        }

        Builder delay(long l, TimeUnit timeUnit) {
            Util.checkNotNull(timeUnit, "timeUnit == null");
            this.delayPublisher = Flowable.just(timeUnit.toMillis(l)).repeat();
            return this;
        }

        Builder exponentialBackoff(long l, TimeUnit timeUnit, float f) {
            Util.checkNotNull(timeUnit, "timeUnit == null");
            this.delayPublisher = Flowable.range(1, Integer.MAX_VALUE).map(RxRetryHandler$Builder$$Lambda$2.lambdaFactory$(f, timeUnit, l));
            return this;
        }

        /* synthetic */ Flowable lambda$build$4(Flowable flowable) throws Exception {
            return flowable.zipWith(this.delayPublisher.concatWith(Flowable.just(-1L)), RxRetryHandler$Builder$$Lambda$4.lambdaFactory$()).flatMap(RxRetryHandler$Builder$$Lambda$5.lambdaFactory$(this)).flatMap(RxRetryHandler$Builder$$Lambda$6.lambdaFactory$(this));
        }

        /* synthetic */ Publisher lambda$null$2(Pair pair) throws Exception {
            if (this.throwablePredicate.test((Throwable)pair.first)) {
                return Flowable.just(pair);
            }
            return Flowable.error((Throwable)pair.first);
        }

        /* synthetic */ Publisher lambda$null$3(Pair pair) throws Exception {
            if ((Long)pair.second <= 0L) {
                return Flowable.error((Throwable)pair.first);
            }
            return Flowable.timer((Long)pair.second, TimeUnit.MILLISECONDS, this.scheduler);
        }

        public Builder maxRetries(int n) {
            this.maxRetries = n;
            return this;
        }

        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = Util.checkNotNull(scheduler, "scheduler == null");
            return this;
        }

        public Builder when(Predicate<? super Throwable> predicate) {
            this.throwablePredicate = Util.checkNotNull(predicate, "predicate == null");
            return this;
        }
    }

}

