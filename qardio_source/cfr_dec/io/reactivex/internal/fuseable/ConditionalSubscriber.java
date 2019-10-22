/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.FlowableSubscriber;

public interface ConditionalSubscriber<T>
extends FlowableSubscriber<T> {
    public boolean tryOnNext(T var1);
}

