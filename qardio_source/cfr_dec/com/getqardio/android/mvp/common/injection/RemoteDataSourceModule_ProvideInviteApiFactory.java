/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class RemoteDataSourceModule_ProvideInviteApiFactory
implements Factory<InviteApi> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final RemoteDataSourceModule module;
    private final Provider<Retrofit> retrofitProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideInviteApiFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideInviteApiFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<Retrofit> provider) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.retrofitProvider = provider;
    }

    public static Factory<InviteApi> create(RemoteDataSourceModule remoteDataSourceModule, Provider<Retrofit> provider) {
        return new RemoteDataSourceModule_ProvideInviteApiFactory(remoteDataSourceModule, provider);
    }

    @Override
    public InviteApi get() {
        return Preconditions.checkNotNull(this.module.provideInviteApi(this.retrofitProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

