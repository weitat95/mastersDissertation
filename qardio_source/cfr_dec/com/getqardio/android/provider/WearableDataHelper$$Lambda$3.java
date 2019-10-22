/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.provider;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.provider.WearableDataHelper;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class WearableDataHelper$$Lambda$3
implements Predicate {
    private static final WearableDataHelper$$Lambda$3 instance = new WearableDataHelper$$Lambda$3();

    private WearableDataHelper$$Lambda$3() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return WearableDataHelper.lambda$getFollowingData$1((IFollowUser)object);
    }
}

