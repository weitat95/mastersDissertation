/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import java.lang.invoke.LambdaForm;

final class FollowMeFragment$$Lambda$1
implements SwipeRefreshLayout.OnRefreshListener {
    private final FollowMeFragment arg$1;

    private FollowMeFragment$$Lambda$1(FollowMeFragment followMeFragment) {
        this.arg$1 = followMeFragment;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(FollowMeFragment followMeFragment) {
        return new FollowMeFragment$$Lambda$1(followMeFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$initViews$0();
    }
}

