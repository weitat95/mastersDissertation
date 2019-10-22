/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityRepository;
import com.getqardio.android.mvp.common.injection.RepositoryModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideActivityTrackerHistoryRepoFactory
implements Factory<HistoryActivityRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HistoryActivityDataSource> localDataSourceProvider;
    private final RepositoryModule module;
    private final Provider<HistoryActivityDataSource> remoteDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideActivityTrackerHistoryRepoFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideActivityTrackerHistoryRepoFactory(RepositoryModule repositoryModule, Provider<HistoryActivityDataSource> provider, Provider<HistoryActivityDataSource> provider2) {
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

    public static Factory<HistoryActivityRepository> create(RepositoryModule repositoryModule, Provider<HistoryActivityDataSource> provider, Provider<HistoryActivityDataSource> provider2) {
        return new RepositoryModule_ProvideActivityTrackerHistoryRepoFactory(repositoryModule, provider, provider2);
    }

    @Override
    public HistoryActivityRepository get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackerHistoryRepo(this.localDataSourceProvider.get(), this.remoteDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

