/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingAddressUpdateInteractor$$Lambda$3
implements Function {
    private static final RealCheckoutShippingAddressUpdateInteractor$$Lambda$3 instance = new RealCheckoutShippingAddressUpdateInteractor$$Lambda$3();

    private RealCheckoutShippingAddressUpdateInteractor$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutShippingAddressUpdateInteractor.lambda$execute$1((Throwable)object);
    }
}

