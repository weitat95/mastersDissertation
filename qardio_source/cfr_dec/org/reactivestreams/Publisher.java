/*
 * Decompiled with CFR 0.147.
 */
package org.reactivestreams;

import org.reactivestreams.Subscriber;

public interface Publisher<T> {
    public void subscribe(Subscriber<? super T> var1);
}

