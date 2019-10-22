/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import java.lang.invoke.LambdaForm;
import java.util.Comparator;

final class IFollowPresenter$$Lambda$10
implements Comparator {
    private static final IFollowPresenter$$Lambda$10 instance = new IFollowPresenter$$Lambda$10();

    private IFollowPresenter$$Lambda$10() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public int compare(Object object, Object object2) {
        return IFollowPresenter.lambda$null$1((IFollowUser)object, (IFollowUser)object2);
    }
}

