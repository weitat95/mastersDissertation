/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideRealmFactory
implements Factory<Realm> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<RealmConfiguration> configProvider;
    private final LocalDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideRealmFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideRealmFactory(LocalDataSourceModule localDataSourceModule, Provider<RealmConfiguration> provider) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.configProvider = provider;
    }

    public static Factory<Realm> create(LocalDataSourceModule localDataSourceModule, Provider<RealmConfiguration> provider) {
        return new LocalDataSourceModule_ProvideRealmFactory(localDataSourceModule, provider);
    }

    @Override
    public Realm get() {
        return Preconditions.checkNotNull(this.module.provideRealm(this.configProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

