/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import com.getqardio.android.mvp.activity.ActivityTrackingLocalDataSource;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.Realm;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory
implements Factory<ActivityTrackingLocalDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Context> contextProvider;
    private final LocalDataSourceModule module;
    private final Provider<Realm> realmProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider, Provider<Context> provider2) {
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
        this.contextProvider = provider2;
    }

    public static Factory<ActivityTrackingLocalDataSource> create(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider, Provider<Context> provider2) {
        return new LocalDataSourceModule_ProvideActivityTrackingLocalDataSourceFactory(localDataSourceModule, provider, provider2);
    }

    @Override
    public ActivityTrackingLocalDataSource get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackingLocalDataSource(this.realmProvider.get(), this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

