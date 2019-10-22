/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class FollowMeListAdapter_Factory
implements Factory<FollowMeListAdapter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final MembersInjector<FollowMeListAdapter> followMeListAdapterMembersInjector;
    private final Provider<ItemCheckedChecker> itemCheckedCheckerProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMeListAdapter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMeListAdapter_Factory(MembersInjector<FollowMeListAdapter> membersInjector, Provider<ItemCheckedChecker> provider) {
        if (!$assertionsDisabled && membersInjector == null) {
            throw new AssertionError();
        }
        this.followMeListAdapterMembersInjector = membersInjector;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.itemCheckedCheckerProvider = provider;
    }

    public static Factory<FollowMeListAdapter> create(MembersInjector<FollowMeListAdapter> membersInjector, Provider<ItemCheckedChecker> provider) {
        return new FollowMeListAdapter_Factory(membersInjector, provider);
    }

    @Override
    public FollowMeListAdapter get() {
        return MembersInjectors.injectMembers(this.followMeListAdapterMembersInjector, new FollowMeListAdapter(this.itemCheckedCheckerProvider.get()));
    }
}

