/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class IFollowPresenter$$Lambda$2
implements Consumer {
    private final IFollowPresenter arg$1;

    private IFollowPresenter$$Lambda$2(IFollowPresenter iFollowPresenter) {
        this.arg$1 = iFollowPresenter;
    }

    public static Consumer lambdaFactory$(IFollowPresenter iFollowPresenter) {
        return new IFollowPresenter$$Lambda$2(iFollowPresenter);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$fetchData$2((List)object);
    }
}

