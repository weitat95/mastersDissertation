/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class FollowMeFragment$$Lambda$3
implements Consumer {
    private final FollowMeFragment arg$1;

    private FollowMeFragment$$Lambda$3(FollowMeFragment followMeFragment) {
        this.arg$1 = followMeFragment;
    }

    public static Consumer lambdaFactory$(FollowMeFragment followMeFragment) {
        return new FollowMeFragment$$Lambda$3(followMeFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$initViews$2((Integer)object);
    }
}

