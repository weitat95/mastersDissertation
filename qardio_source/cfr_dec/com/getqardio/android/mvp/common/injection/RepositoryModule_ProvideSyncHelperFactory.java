/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RepositoryModule;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.provider.SyncHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RepositoryModule_ProvideSyncHelperFactory
implements Factory<SyncHelper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FollowMeUserRepository> followMeUserRepositoryProvider;
    private final Provider<IFollowUserRepository> iFollowUserRepositoryProvider;
    private final RepositoryModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RepositoryModule_ProvideSyncHelperFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RepositoryModule_ProvideSyncHelperFactory(RepositoryModule repositoryModule, Provider<IFollowUserRepository> provider, Provider<FollowMeUserRepository> provider2) {
        if (!$assertionsDisabled && repositoryModule == null) {
            throw new AssertionError();
        }
        this.module = repositoryModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.iFollowUserRepositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.followMeUserRepositoryProvider = provider2;
    }

    public static Factory<SyncHelper> create(RepositoryModule repositoryModule, Provider<IFollowUserRepository> provider, Provider<FollowMeUserRepository> provider2) {
        return new RepositoryModule_ProvideSyncHelperFactory(repositoryModule, provider, provider2);
    }

    @Override
    public SyncHelper get() {
        return Preconditions.checkNotNull(this.module.provideSyncHelper(this.iFollowUserRepositoryProvider.get(), this.followMeUserRepositoryProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

