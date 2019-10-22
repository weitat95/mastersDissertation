/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.remote.CommonHeadersInterceptor;
import com.getqardio.android.mvp.common.remote.UnauthorisedInterceptor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

public final class RemoteDataSourceModule_ProvideOkHttpClientFactory
implements Factory<OkHttpClient> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CommonHeadersInterceptor> commonHeadersInterceptorProvider;
    private final RemoteDataSourceModule module;
    private final Provider<UnauthorisedInterceptor> unauthorisedInterceptorProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideOkHttpClientFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideOkHttpClientFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<UnauthorisedInterceptor> provider, Provider<CommonHeadersInterceptor> provider2) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.unauthorisedInterceptorProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.commonHeadersInterceptorProvider = provider2;
    }

    public static Factory<OkHttpClient> create(RemoteDataSourceModule remoteDataSourceModule, Provider<UnauthorisedInterceptor> provider, Provider<CommonHeadersInterceptor> provider2) {
        return new RemoteDataSourceModule_ProvideOkHttpClientFactory(remoteDataSourceModule, provider, provider2);
    }

    @Override
    public OkHttpClient get() {
        return Preconditions.checkNotNull(this.module.provideOkHttpClient(this.unauthorisedInterceptorProvider.get(), this.commonHeadersInterceptorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

