/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity.ActivityTrackingLocalDataSource;
import com.getqardio.android.mvp.activity.ActivityTrackingRepository;
import com.getqardio.android.mvp.common.injection.RepositoryModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideActivityTrackingRepositoryFactory
implements Factory<ActivityTrackingRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<ActivityTrackingLocalDataSource> localDataSourceProvider;
    private final RepositoryModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideActivityTrackingRepositoryFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideActivityTrackingRepositoryFactory(RepositoryModule repositoryModule, Provider<ActivityTrackingLocalDataSource> provider) {
        if (!$assertionsDisabled && repositoryModule == null) {
            throw new AssertionError();
        }
        this.module = repositoryModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.localDataSourceProvider = provider;
    }

    public static Factory<ActivityTrackingRepository> create(RepositoryModule repositoryModule, Provider<ActivityTrackingLocalDataSource> provider) {
        return new RepositoryModule_ProvideActivityTrackingRepositoryFactory(repositoryModule, provider);
    }

    @Override
    public ActivityTrackingRepository get() {
        return Preconditions.checkNotNull(this.module.provideActivityTrackingRepository(this.localDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

