/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RepositoryModule;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideIFollowRepoFactory
implements Factory<IFollowUserRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<IFollowUserDataSource> localDataSourceProvider;
    private final RepositoryModule module;
    private final Provider<IFollowUserDataSource> remoteDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideIFollowRepoFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideIFollowRepoFactory(RepositoryModule repositoryModule, Provider<IFollowUserDataSource> provider, Provider<IFollowUserDataSource> provider2) {
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

    public static Factory<IFollowUserRepository> create(RepositoryModule repositoryModule, Provider<IFollowUserDataSource> provider, Provider<IFollowUserDataSource> provider2) {
        return new RepositoryModule_ProvideIFollowRepoFactory(repositoryModule, provider, provider2);
    }

    @Override
    public IFollowUserRepository get() {
        return Preconditions.checkNotNull(this.module.provideIFollowRepo(this.localDataSourceProvider.get(), this.remoteDataSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

