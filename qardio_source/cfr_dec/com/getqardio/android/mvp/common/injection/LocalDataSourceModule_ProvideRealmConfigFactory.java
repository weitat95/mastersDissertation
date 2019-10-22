/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.RealmConfiguration;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideRealmConfigFactory
implements Factory<RealmConfiguration> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Context> contextProvider;
    private final LocalDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideRealmConfigFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideRealmConfigFactory(LocalDataSourceModule localDataSourceModule, Provider<Context> provider) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.contextProvider = provider;
    }

    public static Factory<RealmConfiguration> create(LocalDataSourceModule localDataSourceModule, Provider<Context> provider) {
        return new LocalDataSourceModule_ProvideRealmConfigFactory(localDataSourceModule, provider);
    }

    @Override
    public RealmConfiguration get() {
        return Preconditions.checkNotNull(this.module.provideRealmConfig(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

