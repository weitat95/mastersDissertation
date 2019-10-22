/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import java.util.Random;

class RandomBackoff
implements Backoff {
    final Backoff backoff;
    final double jitterPercent;
    final Random random;

    public RandomBackoff(Backoff backoff, double d) {
        this(backoff, d, new Random());
    }

    public RandomBackoff(Backoff backoff, double d, Random random) {
        if (d < 0.0 || d > 1.0) {
            throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
        }
        if (backoff == null) {
            throw new NullPointerException("backoff must not be null");
        }
        if (random == null) {
            throw new NullPointerException("random must not be null");
        }
        this.backoff = backoff;
        this.jitterPercent = d;
        this.random = random;
    }

    @Override
    public long getDelayMillis(int n) {
        return (long)(this.randomJitter() * (double)this.backoff.getDelayMillis(n));
    }

    double randomJitter() {
        double d = 1.0 - this.jitterPercent;
        return (1.0 + this.jitterPercent - d) * this.random.nextDouble() + d;
    }
}

