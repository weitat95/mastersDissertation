/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class FollowMePresenter$$Lambda$6
implements Action {
    private final FollowMePresenter arg$1;

    private FollowMePresenter$$Lambda$6(FollowMePresenter followMePresenter) {
        this.arg$1 = followMePresenter;
    }

    public static Action lambdaFactory$(FollowMePresenter followMePresenter) {
        return new FollowMePresenter$$Lambda$6(followMePresenter);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$deleteSelectedUsers$5();
    }
}

