/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;

public class IFollowPresenterModule {
    private final IFollowFragment view;

    public IFollowPresenterModule() {
        this.view = null;
    }

    public IFollowPresenterModule(IFollowFragment iFollowFragment) {
        this.view = iFollowFragment;
    }

    ItemCheckedChecker provideItemCheckedChecker() {
        return this.view;
    }

    IFollowContract.View provideView() {
        return this.view;
    }
}

