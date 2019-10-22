/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;

public class FollowMeAddFollowingDialogPresenterModule {
    private final FollowMeAddFollowingDialogContract.View view;

    public FollowMeAddFollowingDialogPresenterModule(FollowMeAddFollowingDialogContract.View view) {
        this.view = view;
    }

    FollowMeAddFollowingDialogContract.View provideView() {
        return this.view;
    }
}

