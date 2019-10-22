/*
 * Decompiled with CFR 0.147.
 */
package org.reactivestreams;

import org.reactivestreams.Subscription;

public interface Subscriber<T> {
    public void onComplete();

    public void onError(Throwable var1);

    public void onNext(T var1);

    public void onSubscribe(Subscription var1);
}

