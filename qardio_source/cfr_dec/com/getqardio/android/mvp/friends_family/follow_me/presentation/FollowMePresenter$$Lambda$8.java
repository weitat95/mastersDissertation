/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class FollowMePresenter$$Lambda$8
implements Consumer {
    private static final FollowMePresenter$$Lambda$8 instance = new FollowMePresenter$$Lambda$8();

    private FollowMePresenter$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

