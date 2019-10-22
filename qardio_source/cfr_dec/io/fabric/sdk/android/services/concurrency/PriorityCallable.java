/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.PriorityTask;
import java.util.concurrent.Callable;

public abstract class PriorityCallable<V>
extends PriorityTask
implements Callable<V> {
}

