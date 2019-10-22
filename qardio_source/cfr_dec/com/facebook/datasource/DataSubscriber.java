/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.datasource;

import com.facebook.datasource.DataSource;

public interface DataSubscriber<T> {
    public void onCancellation(DataSource<T> var1);

    public void onFailure(DataSource<T> var1);

    public void onNewResult(DataSource<T> var1);

    public void onProgressUpdate(DataSource<T> var1);
}

