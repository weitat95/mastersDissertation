/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

public final class RemoteDataSourceModule_ProvideServerInterfaceFactory
implements Factory<ServerInterface> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Gson> gsonProvider;
    private final RemoteDataSourceModule module;
    private final Provider<OkHttpClient> okHttpClientProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideServerInterfaceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideServerInterfaceFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<Gson> provider, Provider<OkHttpClient> provider2) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.gsonProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.okHttpClientProvider = provider2;
    }

    public static Factory<ServerInterface> create(RemoteDataSourceModule remoteDataSourceModule, Provider<Gson> provider, Provider<OkHttpClient> provider2) {
        return new RemoteDataSourceModule_ProvideServerInterfaceFactory(remoteDataSourceModule, provider, provider2);
    }

    @Override
    public ServerInterface get() {
        return Preconditions.checkNotNull(this.module.provideServerInterface(this.gsonProvider.get(), this.okHttpClientProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

