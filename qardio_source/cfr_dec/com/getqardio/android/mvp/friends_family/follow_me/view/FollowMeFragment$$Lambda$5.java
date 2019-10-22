/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import java.lang.invoke.LambdaForm;

final class FollowMeFragment$$Lambda$5
implements Runnable {
    private final FollowMeFragment arg$1;

    private FollowMeFragment$$Lambda$5(FollowMeFragment followMeFragment) {
        this.arg$1 = followMeFragment;
    }

    public static Runnable lambdaFactory$(FollowMeFragment followMeFragment) {
        return new FollowMeFragment$$Lambda$5(followMeFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$setUserVisibleHint$4();
    }
}

