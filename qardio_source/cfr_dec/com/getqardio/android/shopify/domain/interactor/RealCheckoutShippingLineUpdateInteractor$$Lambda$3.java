/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingLineUpdateInteractor$$Lambda$3
implements Function {
    private static final RealCheckoutShippingLineUpdateInteractor$$Lambda$3 instance = new RealCheckoutShippingLineUpdateInteractor$$Lambda$3();

    private RealCheckoutShippingLineUpdateInteractor$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutShippingLineUpdateInteractor.lambda$execute$1((Throwable)object);
    }
}

