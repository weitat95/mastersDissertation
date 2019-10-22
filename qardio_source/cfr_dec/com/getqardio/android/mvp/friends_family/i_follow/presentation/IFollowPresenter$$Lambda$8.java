/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class IFollowPresenter$$Lambda$8
implements Consumer {
    private static final IFollowPresenter$$Lambda$8 instance = new IFollowPresenter$$Lambda$8();

    private IFollowPresenter$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        IFollowPresenter.lambda$deleteSelectedUsers$8((IFollowUser)object);
    }
}

