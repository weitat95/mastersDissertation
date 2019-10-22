/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.common.injection.RepositoryModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideGoalForActivityTypeRepoFactory
implements Factory<GoalForActivityTypeRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<GoalForActivityTypeDataSource> localDataSourceProvider;
    private final RepositoryModule module;
    private final Provider<GoalForActivityTypeDataSource> remoteDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideGoalForActivityTypeRepoFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideGoalForActivityTypeRepoFactory(RepositoryModule repositoryModule, Provider<GoalForActivityTypeDataSource> provider, Provider<GoalForActivityTypeDataSource> provider2) {
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

    public static Factory<GoalForActivityTypeRepository> create(RepositoryModule repositoryModule, Provider<GoalForActivityTypeDataSource> provider, Provider<GoalForActivityTypeDataSource> provider2) {
        return new RepositoryModule_ProvideGoalForActivityTypeRepoFactory(repositoryModule, provider, provider2);
    }

    @Override
    public GoalForActivityTypeRepository get() {
        return Preconditions.checkNotNull(this.module.provideGoalForActivityTypeRepo(this.localDataSourceProvider.get(), this.remoteDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

