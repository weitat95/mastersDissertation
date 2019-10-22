/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class IFollowPresenter$$Lambda$7
implements Action {
    private final IFollowPresenter arg$1;

    private IFollowPresenter$$Lambda$7(IFollowPresenter iFollowPresenter) {
        this.arg$1 = iFollowPresenter;
    }

    public static Action lambdaFactory$(IFollowPresenter iFollowPresenter) {
        return new IFollowPresenter$$Lambda$7(iFollowPresenter);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$deleteSelectedUsers$7();
    }
}

