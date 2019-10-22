/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class IFollowPresenter$$Lambda$6
implements Function {
    private final IFollowPresenter arg$1;
    private final Long arg$2;

    private IFollowPresenter$$Lambda$6(IFollowPresenter iFollowPresenter, Long l) {
        this.arg$1 = iFollowPresenter;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(IFollowPresenter iFollowPresenter, Long l) {
        return new IFollowPresenter$$Lambda$6(iFollowPresenter, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$deleteSelectedUsers$6(this.arg$2, (IFollowUser)object);
    }
}

