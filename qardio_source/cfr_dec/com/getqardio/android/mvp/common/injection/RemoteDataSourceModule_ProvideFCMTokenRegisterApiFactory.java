/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.fcm.api.PushNotificationApi;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory
implements Factory<PushNotificationApi> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final RemoteDataSourceModule module;
    private final Provider<Retrofit> retrofitProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<Retrofit> provider) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.retrofitProvider = provider;
    }

    public static Factory<PushNotificationApi> create(RemoteDataSourceModule remoteDataSourceModule, Provider<Retrofit> provider) {
        return new RemoteDataSourceModule_ProvideFCMTokenRegisterApiFactory(remoteDataSourceModule, provider);
    }

    @Override
    public PushNotificationApi get() {
        return Preconditions.checkNotNull(this.module.provideFCMTokenRegisterApi(this.retrofitProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

