/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class IFollowListAdapter_Factory
implements Factory<IFollowListAdapter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final MembersInjector<IFollowListAdapter> iFollowListAdapterMembersInjector;
    private final Provider<ItemCheckedChecker> itemCheckedCheckerProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IFollowListAdapter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IFollowListAdapter_Factory(MembersInjector<IFollowListAdapter> membersInjector, Provider<ItemCheckedChecker> provider) {
        if (!$assertionsDisabled && membersInjector == null) {
            throw new AssertionError();
        }
        this.iFollowListAdapterMembersInjector = membersInjector;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.itemCheckedCheckerProvider = provider;
    }

    public static Factory<IFollowListAdapter> create(MembersInjector<IFollowListAdapter> membersInjector, Provider<ItemCheckedChecker> provider) {
        return new IFollowListAdapter_Factory(membersInjector, provider);
    }

    @Override
    public IFollowListAdapter get() {
        return MembersInjectors.injectMembers(this.iFollowListAdapterMembersInjector, new IFollowListAdapter(this.itemCheckedCheckerProvider.get()));
    }
}

