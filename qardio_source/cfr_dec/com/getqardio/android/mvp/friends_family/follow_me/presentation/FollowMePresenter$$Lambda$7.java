/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FollowMePresenter$$Lambda$7
implements Consumer {
    private static final FollowMePresenter$$Lambda$7 instance = new FollowMePresenter$$Lambda$7();

    private FollowMePresenter$$Lambda$7() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        FollowMePresenter.lambda$deleteSelectedUsers$6((FollowMeUser)object);
    }
}

