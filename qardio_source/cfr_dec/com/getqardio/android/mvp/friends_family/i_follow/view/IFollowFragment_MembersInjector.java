/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class IFollowFragment_MembersInjector
implements MembersInjector<IFollowFragment> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<IFollowListAdapter> adapterProvider;
    private final Provider<IFollowPresenter> presenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IFollowFragment_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IFollowFragment_MembersInjector(Provider<IFollowPresenter> provider, Provider<IFollowListAdapter> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.presenterProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.adapterProvider = provider2;
    }

    public static MembersInjector<IFollowFragment> create(Provider<IFollowPresenter> provider, Provider<IFollowListAdapter> provider2) {
        return new IFollowFragment_MembersInjector(provider, provider2);
    }

    @Override
    public void injectMembers(IFollowFragment iFollowFragment) {
        if (iFollowFragment == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        iFollowFragment.presenter = this.presenterProvider.get();
        iFollowFragment.adapter = this.adapterProvider.get();
    }
}

