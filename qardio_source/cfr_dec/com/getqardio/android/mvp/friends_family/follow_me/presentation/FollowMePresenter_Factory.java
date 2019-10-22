/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.common.ui.MultiChoiceMode;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FollowMePresenter_Factory
implements Factory<FollowMePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<MultiChoiceMode> multiChoiceModeProvider;
    private final Provider<FollowMeUserRepository> repositoryProvider;
    private final Provider<FollowMeFragmentContract.View> viewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMePresenter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMePresenter_Factory(Provider<FollowMeUserRepository> provider, Provider<FollowMeFragmentContract.View> provider2, Provider<MultiChoiceMode> provider3) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.repositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.viewProvider = provider2;
        if (!$assertionsDisabled && provider3 == null) {
            throw new AssertionError();
        }
        this.multiChoiceModeProvider = provider3;
    }

    public static Factory<FollowMePresenter> create(Provider<FollowMeUserRepository> provider, Provider<FollowMeFragmentContract.View> provider2, Provider<MultiChoiceMode> provider3) {
        return new FollowMePresenter_Factory(provider, provider2, provider3);
    }

    @Override
    public FollowMePresenter get() {
        return new FollowMePresenter(this.repositoryProvider.get(), this.viewProvider.get(), this.multiChoiceModeProvider.get());
    }
}

