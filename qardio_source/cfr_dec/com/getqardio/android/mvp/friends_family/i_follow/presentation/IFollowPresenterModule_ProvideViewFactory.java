/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class IFollowPresenterModule_ProvideViewFactory
implements Factory<IFollowContract.View> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final IFollowPresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IFollowPresenterModule_ProvideViewFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IFollowPresenterModule_ProvideViewFactory(IFollowPresenterModule iFollowPresenterModule) {
        if (!$assertionsDisabled && iFollowPresenterModule == null) {
            throw new AssertionError();
        }
        this.module = iFollowPresenterModule;
    }

    public static Factory<IFollowContract.View> create(IFollowPresenterModule iFollowPresenterModule) {
        return new IFollowPresenterModule_ProvideViewFactory(iFollowPresenterModule);
    }

    @Override
    public IFollowContract.View get() {
        return Preconditions.checkNotNull(this.module.provideView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

