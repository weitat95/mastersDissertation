/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.remote.UnauthorisedInterceptor;
import com.getqardio.android.mvp.common.util.RxEventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory
implements Factory<UnauthorisedInterceptor> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final RemoteDataSourceModule module;
    private final Provider<RxEventBus> rxEventBusProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<RxEventBus> provider) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.rxEventBusProvider = provider;
    }

    public static Factory<UnauthorisedInterceptor> create(RemoteDataSourceModule remoteDataSourceModule, Provider<RxEventBus> provider) {
        return new RemoteDataSourceModule_ProvideUnathorizedInterceptorFactory(remoteDataSourceModule, provider);
    }

    @Override
    public UnauthorisedInterceptor get() {
        return Preconditions.checkNotNull(this.module.provideUnathorizedInterceptor(this.rxEventBusProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

