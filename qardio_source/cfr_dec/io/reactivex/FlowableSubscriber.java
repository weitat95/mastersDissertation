/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public interface FlowableSubscriber<T>
extends Subscriber<T> {
    @Override
    public void onSubscribe(Subscription var1);
}

