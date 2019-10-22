/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.provider;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class WearableDataHelper$$Lambda$2
implements Function {
    private static final WearableDataHelper$$Lambda$2 instance = new WearableDataHelper$$Lambda$2();

    private WearableDataHelper$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((IFollowUser)object).getWatchingEmail();
    }
}

