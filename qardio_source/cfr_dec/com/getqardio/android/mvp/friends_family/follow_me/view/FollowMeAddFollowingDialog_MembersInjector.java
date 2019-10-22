/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class FollowMeAddFollowingDialog_MembersInjector
implements MembersInjector<FollowMeAddFollowingDialog> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FollowMeAddFollowingDialogPresenter> presenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMeAddFollowingDialog_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMeAddFollowingDialog_MembersInjector(Provider<FollowMeAddFollowingDialogPresenter> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.presenterProvider = provider;
    }

    public static MembersInjector<FollowMeAddFollowingDialog> create(Provider<FollowMeAddFollowingDialogPresenter> provider) {
        return new FollowMeAddFollowingDialog_MembersInjector(provider);
    }

    @Override
    public void injectMembers(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        if (followMeAddFollowingDialog == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        followMeAddFollowingDialog.presenter = this.presenterProvider.get();
    }
}

