/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.ObjectHelper;

abstract class AbstractFlowableWithUpstream<T, R>
extends Flowable<R> {
    protected final Flowable<T> source;

    AbstractFlowableWithUpstream(Flowable<T> flowable) {
        this.source = ObjectHelper.requireNonNull(flowable, "source is null");
    }
}

