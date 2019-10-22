/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class IFollowPresenter$$Lambda$5
implements Consumer {
    private final IFollowPresenter arg$1;
    private final IFollowUser arg$2;
    private final boolean arg$3;
    private final int arg$4;

    private IFollowPresenter$$Lambda$5(IFollowPresenter iFollowPresenter, IFollowUser iFollowUser, boolean bl, int n) {
        this.arg$1 = iFollowPresenter;
        this.arg$2 = iFollowUser;
        this.arg$3 = bl;
        this.arg$4 = n;
    }

    public static Consumer lambdaFactory$(IFollowPresenter iFollowPresenter, IFollowUser iFollowUser, boolean bl, int n) {
        return new IFollowPresenter$$Lambda$5(iFollowPresenter, iFollowUser, bl, n);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$clickOnNotification$5(this.arg$2, this.arg$3, this.arg$4, (Throwable)object);
    }
}

