/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.Realm;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideAccountContextHelperFactory
implements Factory<AccountContextHelper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final LocalDataSourceModule module;
    private final Provider<Realm> realmProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideAccountContextHelperFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideAccountContextHelperFactory(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.realmProvider = provider;
    }

    public static Factory<AccountContextHelper> create(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider) {
        return new LocalDataSourceModule_ProvideAccountContextHelperFactory(localDataSourceModule, provider);
    }

    @Override
    public AccountContextHelper get() {
        return Preconditions.checkNotNull(this.module.provideAccountContextHelper(this.realmProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

