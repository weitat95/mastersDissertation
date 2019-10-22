/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.internal.fuseable.QueueFuseable;
import org.reactivestreams.Subscription;

public interface QueueSubscription<T>
extends QueueFuseable<T>,
Subscription {
}

