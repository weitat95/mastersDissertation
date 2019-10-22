/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.RxUtil;
import com.shopify.buy3.GraphResponse;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$4
implements Function {
    private static final RxUtil$$Lambda$4 instance = new RxUtil$$Lambda$4();

    private RxUtil$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxUtil.lambda$null$3((GraphResponse)object);
    }
}

