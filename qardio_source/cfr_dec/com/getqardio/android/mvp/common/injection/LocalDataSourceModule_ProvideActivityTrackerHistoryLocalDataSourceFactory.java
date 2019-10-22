/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.Realm;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory
implements Factory<HistoryActivityDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final LocalDataSourceModule module;
    private final Provider<Realm> realmProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.realmProvider = provider;
    }

    public static Factory<HistoryActivityDataSource> create(LocalDataSourceModule localDataSourceModule, Provider<Realm> provider) {
        return new LocalDataSourceModule_ProvideActivityTrackerHistoryLocalDataSourceFactory(localDataSourceModule, provider);
    }

    @Override
    public HistoryActivityDataSource get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackerHistoryLocalDataSource(this.realmProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

