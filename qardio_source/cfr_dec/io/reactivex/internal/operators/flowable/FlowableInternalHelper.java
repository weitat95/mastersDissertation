/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscription;

public final class FlowableInternalHelper {

    public static enum RequestMax implements Consumer<Subscription>
    {
        INSTANCE;


        @Override
        public void accept(Subscription subscription) throws Exception {
            subscription.request(Long.MAX_VALUE);
        }
    }

}

