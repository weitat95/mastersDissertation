/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;
import java.lang.invoke.LambdaForm;

final class IFollowFragment$$Lambda$2
implements SwipeRefreshLayout.OnRefreshListener {
    private final IFollowFragment arg$1;

    private IFollowFragment$$Lambda$2(IFollowFragment iFollowFragment) {
        this.arg$1 = iFollowFragment;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(IFollowFragment iFollowFragment) {
        return new IFollowFragment$$Lambda$2(iFollowFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$initViews$1();
    }
}

