/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityRepository;
import com.getqardio.android.mvp.common.injection.RepositoryModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideActivityTrackerRepoFactory
implements Factory<TodayActivityRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<TodayActivityDataSource> localDataSourceProvider;
    private final RepositoryModule module;
    private final Provider<TodayActivityDataSource> remoteDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideActivityTrackerRepoFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideActivityTrackerRepoFactory(RepositoryModule repositoryModule, Provider<TodayActivityDataSource> provider, Provider<TodayActivityDataSource> provider2) {
        if (!$assertionsDisabled && repositoryModule == null) {
            throw new AssertionError();
        }
        this.module = repositoryModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.localDataSourceProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.remoteDataSourceProvider = provider2;
    }

    public static Factory<TodayActivityRepository> create(RepositoryModule repositoryModule, Provider<TodayActivityDataSource> provider, Provider<TodayActivityDataSource> provider2) {
        return new RepositoryModule_ProvideActivityTrackerRepoFactory(repositoryModule, provider, provider2);
    }

    @Override
    public TodayActivityRepository get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackerRepo(this.localDataSourceProvider.get(), this.remoteDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

