/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import android.content.SharedPreferences;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideSharedPreferencesFactory
implements Factory<SharedPreferences> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Context> appContextProvider;
    private final LocalDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideSharedPreferencesFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideSharedPreferencesFactory(LocalDataSourceModule localDataSourceModule, Provider<Context> provider) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.appContextProvider = provider;
    }

    public static Factory<SharedPreferences> create(LocalDataSourceModule localDataSourceModule, Provider<Context> provider) {
        return new LocalDataSourceModule_ProvideSharedPreferencesFactory(localDataSourceModule, provider);
    }

    @Override
    public SharedPreferences get() {
        return Preconditions.checkNotNull(this.module.provideSharedPreferences(this.appContextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

