/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class IFollowPresenterModule_ProvideItemCheckedCheckerFactory
implements Factory<ItemCheckedChecker> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final IFollowPresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IFollowPresenterModule_ProvideItemCheckedCheckerFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IFollowPresenterModule_ProvideItemCheckedCheckerFactory(IFollowPresenterModule iFollowPresenterModule) {
        if (!$assertionsDisabled && iFollowPresenterModule == null) {
            throw new AssertionError();
        }
        this.module = iFollowPresenterModule;
    }

    public static Factory<ItemCheckedChecker> create(IFollowPresenterModule iFollowPresenterModule) {
        return new IFollowPresenterModule_ProvideItemCheckedCheckerFactory(iFollowPresenterModule);
    }

    @Override
    public ItemCheckedChecker get() {
        return Preconditions.checkNotNull(this.module.provideItemCheckedChecker(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

