/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RepositoryModule;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideFollowingMeUserRepoFactory
implements Factory<FollowMeUserRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FollowMeUserDataSource> localDataSourceProvider;
    private final RepositoryModule module;
    private final Provider<FollowMeUserDataSource> remoteDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideFollowingMeUserRepoFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideFollowingMeUserRepoFactory(RepositoryModule repositoryModule, Provider<FollowMeUserDataSource> provider, Provider<FollowMeUserDataSource> provider2) {
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

    public static Factory<FollowMeUserRepository> create(RepositoryModule repositoryModule, Provider<FollowMeUserDataSource> provider, Provider<FollowMeUserDataSource> provider2) {
        return new RepositoryModule_ProvideFollowingMeUserRepoFactory(repositoryModule, provider, provider2);
    }

    @Override
    public FollowMeUserRepository get() {
        return Preconditions.checkNotNull(this.module.provideFollowingMeUserRepo(this.localDataSourceProvider.get(), this.remoteDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

