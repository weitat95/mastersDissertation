/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class IFollowFragment$$Lambda$5
implements Consumer {
    private final IFollowFragment arg$1;

    private IFollowFragment$$Lambda$5(IFollowFragment iFollowFragment) {
        this.arg$1 = iFollowFragment;
    }

    public static Consumer lambdaFactory$(IFollowFragment iFollowFragment) {
        return new IFollowFragment$$Lambda$5(iFollowFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$initViews$4((Integer)object);
    }
}

