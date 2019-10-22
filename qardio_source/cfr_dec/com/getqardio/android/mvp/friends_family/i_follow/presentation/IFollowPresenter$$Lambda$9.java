/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class IFollowPresenter$$Lambda$9
implements Consumer {
    private static final IFollowPresenter$$Lambda$9 instance = new IFollowPresenter$$Lambda$9();

    private IFollowPresenter$$Lambda$9() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

