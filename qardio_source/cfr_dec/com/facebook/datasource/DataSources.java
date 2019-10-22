/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.datasource;

import com.facebook.common.internal.Supplier;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.SimpleDataSource;

public class DataSources {
    public static <T> Supplier<DataSource<T>> getFailedDataSourceSupplier(final Throwable throwable) {
        return new Supplier<DataSource<T>>(){

            @Override
            public DataSource<T> get() {
                return DataSources.immediateFailedDataSource(throwable);
            }
        };
    }

    public static <T> DataSource<T> immediateFailedDataSource(Throwable throwable) {
        SimpleDataSource simpleDataSource = SimpleDataSource.create();
        simpleDataSource.setFailure(throwable);
        return simpleDataSource;
    }

}

