/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import retrofit2.Retrofit;

public final class RemoteDataSourceModule_GetSimplifiedRetrofitFactory
implements Factory<Retrofit> {
    private static final RemoteDataSourceModule_GetSimplifiedRetrofitFactory INSTANCE = new RemoteDataSourceModule_GetSimplifiedRetrofitFactory();

    public static Factory<Retrofit> create() {
        return INSTANCE;
    }

    @Override
    public Retrofit get() {
        return Preconditions.checkNotNull(RemoteDataSourceModule.getSimplifiedRetrofit(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

