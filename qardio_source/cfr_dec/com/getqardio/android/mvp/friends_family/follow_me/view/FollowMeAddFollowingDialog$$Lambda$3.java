/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class FollowMeAddFollowingDialog$$Lambda$3
implements Action {
    private final FollowMeAddFollowingDialog arg$1;

    private FollowMeAddFollowingDialog$$Lambda$3(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        this.arg$1 = followMeAddFollowingDialog;
    }

    public static Action lambdaFactory$(FollowMeAddFollowingDialog followMeAddFollowingDialog) {
        return new FollowMeAddFollowingDialog$$Lambda$3(followMeAddFollowingDialog);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$checkContactPermissions$2();
    }
}

