/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import java.lang.invoke.LambdaForm;
import java.util.Comparator;

final class FollowMePresenter$$Lambda$4
implements Comparator {
    private static final FollowMePresenter$$Lambda$4 instance = new FollowMePresenter$$Lambda$4();

    private FollowMePresenter$$Lambda$4() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public int compare(Object object, Object object2) {
        return FollowMePresenter.lambda$sortUsers$3((FollowMeUser)object, (FollowMeUser)object2);
    }
}

