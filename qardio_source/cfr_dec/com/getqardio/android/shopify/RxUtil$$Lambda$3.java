/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.RxUtil;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$3
implements SingleTransformer {
    private static final RxUtil$$Lambda$3 instance = new RxUtil$$Lambda$3();

    private RxUtil$$Lambda$3() {
    }

    public static SingleTransformer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public SingleSource apply(Single single) {
        return RxUtil.lambda$queryResponseDataTransformer$4(single);
    }
}

