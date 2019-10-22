/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.datasource;

import com.facebook.datasource.DataSubscriber;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public interface DataSource<T> {
    public boolean close();

    @Nullable
    public Throwable getFailureCause();

    public float getProgress();

    @Nullable
    public T getResult();

    public boolean hasResult();

    public boolean isFinished();

    public void subscribe(DataSubscriber<T> var1, Executor var2);
}

