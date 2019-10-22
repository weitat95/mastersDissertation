/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;
import io.reactivex.functions.BiConsumer;
import java.lang.invoke.LambdaForm;

final class IFollowFragment$$Lambda$7
implements BiConsumer {
    private final IFollowFragment arg$1;

    private IFollowFragment$$Lambda$7(IFollowFragment iFollowFragment) {
        this.arg$1 = iFollowFragment;
    }

    public static BiConsumer lambdaFactory$(IFollowFragment iFollowFragment) {
        return new IFollowFragment$$Lambda$7(iFollowFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object, Object object2) {
        this.arg$1.lambda$initViews$6((Integer)object, (Boolean)object2);
    }
}

