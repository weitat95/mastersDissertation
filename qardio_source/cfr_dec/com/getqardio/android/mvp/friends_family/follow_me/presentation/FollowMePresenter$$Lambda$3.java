/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FollowMePresenter$$Lambda$3
implements Consumer {
    private final FollowMePresenter arg$1;
    private final boolean arg$2;

    private FollowMePresenter$$Lambda$3(FollowMePresenter followMePresenter, boolean bl) {
        this.arg$1 = followMePresenter;
        this.arg$2 = bl;
    }

    public static Consumer lambdaFactory$(FollowMePresenter followMePresenter, boolean bl) {
        return new FollowMePresenter$$Lambda$3(followMePresenter, bl);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$fetchData$2(this.arg$2, (Throwable)object);
    }
}

