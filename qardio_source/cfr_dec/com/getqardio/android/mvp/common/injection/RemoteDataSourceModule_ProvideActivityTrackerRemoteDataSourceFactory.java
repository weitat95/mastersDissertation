/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory
implements Factory<TodayActivityDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<AccountContextHelper> accountContextHelperProvider;
    private final RemoteDataSourceModule module;
    private final Provider<ServerInterface> serverInterfaceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory(RemoteDataSourceModule remoteDataSourceModule, Provider<ServerInterface> provider, Provider<AccountContextHelper> provider2) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.serverInterfaceProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.accountContextHelperProvider = provider2;
    }

    public static Factory<TodayActivityDataSource> create(RemoteDataSourceModule remoteDataSourceModule, Provider<ServerInterface> provider, Provider<AccountContextHelper> provider2) {
        return new RemoteDataSourceModule_ProvideActivityTrackerRemoteDataSourceFactory(remoteDataSourceModule, provider, provider2);
    }

    @Override
    public TodayActivityDataSource get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackerRemoteDataSource(this.serverInterfaceProvider.get(), this.accountContextHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

