/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FollowMeAddFollowingDialogPresenter$$Lambda$2
implements Consumer {
    private final FollowMeAddFollowingDialogPresenter arg$1;

    private FollowMeAddFollowingDialogPresenter$$Lambda$2(FollowMeAddFollowingDialogPresenter followMeAddFollowingDialogPresenter) {
        this.arg$1 = followMeAddFollowingDialogPresenter;
    }

    public static Consumer lambdaFactory$(FollowMeAddFollowingDialogPresenter followMeAddFollowingDialogPresenter) {
        return new FollowMeAddFollowingDialogPresenter$$Lambda$2(followMeAddFollowingDialogPresenter);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$invite$1((Throwable)object);
    }
}

