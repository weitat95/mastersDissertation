/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.provider;

import com.getqardio.android.provider.WearableDataHelper;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class WearableDataHelper$$Lambda$1
implements Function {
    private static final WearableDataHelper$$Lambda$1 instance = new WearableDataHelper$$Lambda$1();

    private WearableDataHelper$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return WearableDataHelper.lambda$getFollowingData$0((List)object);
    }
}

