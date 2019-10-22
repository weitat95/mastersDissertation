/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FollowMePresenterModule_ProvideItemCheckedCheckerFactory
implements Factory<ItemCheckedChecker> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final FollowMePresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FollowMePresenterModule_ProvideItemCheckedCheckerFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FollowMePresenterModule_ProvideItemCheckedCheckerFactory(FollowMePresenterModule followMePresenterModule) {
        if (!$assertionsDisabled && followMePresenterModule == null) {
            throw new AssertionError();
        }
        this.module = followMePresenterModule;
    }

    public static Factory<ItemCheckedChecker> create(FollowMePresenterModule followMePresenterModule) {
        return new FollowMePresenterModule_ProvideItemCheckedCheckerFactory(followMePresenterModule);
    }

    @Override
    public ItemCheckedChecker get() {
        return Preconditions.checkNotNull(this.module.provideItemCheckedChecker(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

