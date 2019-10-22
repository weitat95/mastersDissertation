/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory
implements Factory<FollowMeAddFollowingDialogContract.View> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final FollowMeAddFollowingDialogPresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory(FollowMeAddFollowingDialogPresenterModule followMeAddFollowingDialogPresenterModule) {
        if (!$assertionsDisabled && followMeAddFollowingDialogPresenterModule == null) {
            throw new AssertionError();
        }
        this.module = followMeAddFollowingDialogPresenterModule;
    }

    public static Factory<FollowMeAddFollowingDialogContract.View> create(FollowMeAddFollowingDialogPresenterModule followMeAddFollowingDialogPresenterModule) {
        return new FollowMeAddFollowingDialogPresenterModule_ProvideViewFactory(followMeAddFollowingDialogPresenterModule);
    }

    @Override
    public FollowMeAddFollowingDialogContract.View get() {
        return Preconditions.checkNotNull(this.module.provideView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

