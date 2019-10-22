/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class FollowMeFragment_MembersInjector
implements MembersInjector<FollowMeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FollowMeListAdapter> adapterProvider;
    private final Provider<FollowMePresenter> presenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMeFragment_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMeFragment_MembersInjector(Provider<FollowMePresenter> provider, Provider<FollowMeListAdapter> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.presenterProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.adapterProvider = provider2;
    }

    public static MembersInjector<FollowMeFragment> create(Provider<FollowMePresenter> provider, Provider<FollowMeListAdapter> provider2) {
        return new FollowMeFragment_MembersInjector(provider, provider2);
    }

    @Override
    public void injectMembers(FollowMeFragment followMeFragment) {
        if (followMeFragment == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        followMeFragment.presenter = this.presenterProvider.get();
        followMeFragment.adapter = this.adapterProvider.get();
    }
}

