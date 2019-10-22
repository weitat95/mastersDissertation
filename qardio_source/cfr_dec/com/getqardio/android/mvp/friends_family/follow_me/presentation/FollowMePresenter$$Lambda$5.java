/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeUiModelItem;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class FollowMePresenter$$Lambda$5
implements Function {
    private final FollowMePresenter arg$1;
    private final Long arg$2;

    private FollowMePresenter$$Lambda$5(FollowMePresenter followMePresenter, Long l) {
        this.arg$1 = followMePresenter;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(FollowMePresenter followMePresenter, Long l) {
        return new FollowMePresenter$$Lambda$5(followMePresenter, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$deleteSelectedUsers$4(this.arg$2, (FollowMeUiModelItem)object);
    }
}

