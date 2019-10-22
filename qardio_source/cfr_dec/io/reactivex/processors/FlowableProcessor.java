/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.processors;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.processors.SerializedProcessor;
import org.reactivestreams.Processor;

public abstract class FlowableProcessor<T>
extends Flowable<T>
implements FlowableSubscriber<T>,
Processor<T, T> {
    public final FlowableProcessor<T> toSerialized() {
        if (this instanceof SerializedProcessor) {
            return this;
        }
        return new SerializedProcessor(this);
    }
}

