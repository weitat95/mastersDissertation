/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import java.lang.invoke.LambdaForm;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

final class IFollowPresenter$$Lambda$1
implements Publisher {
    private final IFollowPresenter arg$1;

    private IFollowPresenter$$Lambda$1(IFollowPresenter iFollowPresenter) {
        this.arg$1 = iFollowPresenter;
    }

    public static Publisher lambdaFactory$(IFollowPresenter iFollowPresenter) {
        return new IFollowPresenter$$Lambda$1(iFollowPresenter);
    }

    @LambdaForm.Hidden
    public void subscribe(Subscriber subscriber) {
        this.arg$1.lambda$fetchData$0(subscriber);
    }
}

