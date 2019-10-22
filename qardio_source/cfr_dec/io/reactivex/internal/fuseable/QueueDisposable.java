/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.fuseable;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.QueueFuseable;

public interface QueueDisposable<T>
extends Disposable,
QueueFuseable<T> {
}

