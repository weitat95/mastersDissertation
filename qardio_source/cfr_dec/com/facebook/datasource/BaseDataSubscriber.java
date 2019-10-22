/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.datasource;

import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;

public abstract class BaseDataSubscriber<T>
implements DataSubscriber<T> {
    @Override
    public void onCancellation(DataSource<T> dataSource) {
    }

    @Override
    public void onFailure(DataSource<T> dataSource) {
        try {
            this.onFailureImpl(dataSource);
            return;
        }
        finally {
            dataSource.close();
        }
    }

    protected abstract void onFailureImpl(DataSource<T> var1);

    @Override
    public void onNewResult(DataSource<T> dataSource) {
        boolean bl = dataSource.isFinished();
        try {
            this.onNewResultImpl(dataSource);
            return;
        }
        finally {
            if (bl) {
                dataSource.close();
            }
        }
    }

    protected abstract void onNewResultImpl(DataSource<T> var1);

    @Override
    public void onProgressUpdate(DataSource<T> dataSource) {
    }
}

