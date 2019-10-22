/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import java.lang.invoke.LambdaForm;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

final class FollowMePresenter$$Lambda$1
implements Publisher {
    private final FollowMePresenter arg$1;

    private FollowMePresenter$$Lambda$1(FollowMePresenter followMePresenter) {
        this.arg$1 = followMePresenter;
    }

    public static Publisher lambdaFactory$(FollowMePresenter followMePresenter) {
        return new FollowMePresenter$$Lambda$1(followMePresenter);
    }

    @LambdaForm.Hidden
    public void subscribe(Subscriber subscriber) {
        this.arg$1.lambda$fetchData$0(subscriber);
    }
}

