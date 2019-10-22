/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.datasource;

import com.facebook.common.internal.Preconditions;
import com.facebook.datasource.AbstractDataSource;

public class SimpleDataSource<T>
extends AbstractDataSource<T> {
    private SimpleDataSource() {
    }

    public static <T> SimpleDataSource<T> create() {
        return new SimpleDataSource<T>();
    }

    @Override
    public boolean setFailure(Throwable throwable) {
        return super.setFailure(Preconditions.checkNotNull(throwable));
    }

    @Override
    public boolean setProgress(float f) {
        return super.setProgress(f);
    }

    @Override
    public boolean setResult(T t, boolean bl) {
        return super.setResult(Preconditions.checkNotNull(t), bl);
    }
}

