/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FollowMeAddFollowingDialogPresenter_Factory
implements Factory<FollowMeAddFollowingDialogPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FollowMeUserRepository> repositoryProvider;
    private final Provider<FollowMeAddFollowingDialogContract.View> viewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMeAddFollowingDialogPresenter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMeAddFollowingDialogPresenter_Factory(Provider<FollowMeUserRepository> provider, Provider<FollowMeAddFollowingDialogContract.View> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.repositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.viewProvider = provider2;
    }

    public static Factory<FollowMeAddFollowingDialogPresenter> create(Provider<FollowMeUserRepository> provider, Provider<FollowMeAddFollowingDialogContract.View> provider2) {
        return new FollowMeAddFollowingDialogPresenter_Factory(provider, provider2);
    }

    @Override
    public FollowMeAddFollowingDialogPresenter get() {
        return new FollowMeAddFollowingDialogPresenter(this.repositoryProvider.get(), this.viewProvider.get());
    }
}

