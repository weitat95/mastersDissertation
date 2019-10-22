/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.Realm;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory
implements Factory<IFollowUserDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<AccountContextHelper> accountContextHelperProvider;
    private final LocalDataSourceModule module;
    private final Provider<Realm> realmProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider, Provider<AccountContextHelper> provider2) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.realmProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.accountContextHelperProvider = provider2;
    }

    public static Factory<IFollowUserDataSource> create(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider, Provider<AccountContextHelper> provider2) {
        return new LocalDataSourceModule_ProvideIFollowUserLocalDataSourceFactory(localDataSourceModule, provider, provider2);
    }

    @Override
    public IFollowUserDataSource get() {
        return Preconditions.checkNotNull(this.module.provideIFollowUserLocalDataSource(this.realmProvider.get(), this.accountContextHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

