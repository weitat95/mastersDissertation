/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FollowMePresenterModule_ProvideViewFactory
implements Factory<FollowMeFragmentContract.View> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final FollowMePresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMePresenterModule_ProvideViewFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMePresenterModule_ProvideViewFactory(FollowMePresenterModule followMePresenterModule) {
        if (!$assertionsDisabled && followMePresenterModule == null) {
            throw new AssertionError();
        }
        this.module = followMePresenterModule;
    }

    public static Factory<FollowMeFragmentContract.View> create(FollowMePresenterModule followMePresenterModule) {
        return new FollowMePresenterModule_ProvideViewFactory(followMePresenterModule);
    }

    @Override
    public FollowMeFragmentContract.View get() {
        return Preconditions.checkNotNull(this.module.provideView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

