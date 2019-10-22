/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class FollowMeAddFollowingDialog$$Lambda$4
implements Action {
    private static final FollowMeAddFollowingDialog$$Lambda$4 instance = new FollowMeAddFollowingDialog$$Lambda$4();

    private FollowMeAddFollowingDialog$$Lambda$4() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        FollowMeAddFollowingDialog.lambda$checkContactPermissions$3();
    }
}

