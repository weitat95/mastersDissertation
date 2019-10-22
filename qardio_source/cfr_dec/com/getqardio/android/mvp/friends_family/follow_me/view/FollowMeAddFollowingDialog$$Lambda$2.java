/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import java.lang.invoke.LambdaForm;

final class FollowMeAddFollowingDialog$$Lambda$2
implements Runnable {
    private final FollowMeAddFollowingDialog arg$1;

    private FollowMeAddFollowingDialog$$Lambda$2(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        this.arg$1 = followMeAddFollowingDialog;
    }

    public static Runnable lambdaFactory$(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        return new FollowMeAddFollowingDialog$$Lambda$2(followMeAddFollowingDialog);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$initViews$1();
    }
}

