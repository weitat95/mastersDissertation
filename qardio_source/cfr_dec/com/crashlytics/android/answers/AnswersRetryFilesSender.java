/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.RandomBackoff;
import com.crashlytics.android.answers.RetryManager;
import com.crashlytics.android.answers.SessionAnalyticsFilesSender;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.DefaultRetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.ExponentialBackoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryState;
import io.fabric.sdk.android.services.events.FilesSender;
import java.io.File;
import java.util.List;

class AnswersRetryFilesSender
implements FilesSender {
    private final SessionAnalyticsFilesSender filesSender;
    private final RetryManager retryManager;

    AnswersRetryFilesSender(SessionAnalyticsFilesSender sessionAnalyticsFilesSender, RetryManager retryManager) {
        this.filesSender = sessionAnalyticsFilesSender;
        this.retryManager = retryManager;
    }

    public static AnswersRetryFilesSender build(SessionAnalyticsFilesSender sessionAnalyticsFilesSender) {
        return new AnswersRetryFilesSender(sessionAnalyticsFilesSender, new RetryManager(new RetryState(new RandomBackoff(new ExponentialBackoff(1000L, 8), 0.1), new DefaultRetryPolicy(5))));
    }

    @Override
    public boolean send(List<File> list) {
        long l;
        block3: {
            boolean bl;
            block2: {
                bl = false;
                l = System.nanoTime();
                if (!this.retryManager.canRetry(l)) break block2;
                if (!this.filesSender.send(list)) break block3;
                this.retryManager.reset();
                bl = true;
            }
            return bl;
        }
        this.retryManager.recordRetry(l);
        return false;
    }
}

