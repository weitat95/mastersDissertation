/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;

public class FollowMePresenterModule {
    private final FollowMeFragment view;

    public FollowMePresenterModule(FollowMeFragment followMeFragment) {
        this.view = followMeFragment;
    }

    ItemCheckedChecker provideItemCheckedChecker() {
        return this.view;
    }

    FollowMeFragmentContract.View provideView() {
        return this.view;
    }
}

