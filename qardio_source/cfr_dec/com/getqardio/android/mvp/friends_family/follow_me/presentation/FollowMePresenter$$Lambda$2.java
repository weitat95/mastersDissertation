/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class FollowMePresenter$$Lambda$2
implements Consumer {
    private final FollowMePresenter arg$1;

    private FollowMePresenter$$Lambda$2(FollowMePresenter followMePresenter) {
        this.arg$1 = followMePresenter;
    }

    public static Consumer lambdaFactory$(FollowMePresenter followMePresenter) {
        return new FollowMePresenter$$Lambda$2(followMePresenter);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$fetchData$1((List)object);
    }
}

