/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.RxUtil;
import com.getqardio.android.shopify.util.BiFunction;
import com.shopify.graphql.support.Error;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$5
implements BiFunction {
    private static final RxUtil$$Lambda$5 instance = new RxUtil$$Lambda$5();

    private RxUtil$$Lambda$5() {
    }

    public static BiFunction lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2) {
        return RxUtil.lambda$null$2((StringBuilder)object, (Error)object2);
    }
}

