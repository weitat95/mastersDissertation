/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.api.Releasable;

public interface DataBuffer<T>
extends Releasable,
Iterable<T> {
    public T get(int var1);

    public int getCount();
}

