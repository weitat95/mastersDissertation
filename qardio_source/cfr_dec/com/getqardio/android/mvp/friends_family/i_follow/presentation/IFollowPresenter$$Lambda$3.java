/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class IFollowPresenter$$Lambda$3
implements Consumer {
    private final IFollowPresenter arg$1;
    private final boolean arg$2;

    private IFollowPresenter$$Lambda$3(IFollowPresenter iFollowPresenter, boolean bl) {
        this.arg$1 = iFollowPresenter;
        this.arg$2 = bl;
    }

    public static Consumer lambdaFactory$(IFollowPresenter iFollowPresenter, boolean bl) {
        return new IFollowPresenter$$Lambda$3(iFollowPresenter, bl);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$fetchData$3(this.arg$2, (Throwable)object);
    }
}

