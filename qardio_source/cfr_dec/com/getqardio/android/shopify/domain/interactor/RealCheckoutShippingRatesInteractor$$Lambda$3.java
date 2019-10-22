/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor;
import com.getqardio.android.shopify.domain.model.Checkout;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingRatesInteractor$$Lambda$3
implements Function {
    private static final RealCheckoutShippingRatesInteractor$$Lambda$3 instance = new RealCheckoutShippingRatesInteractor$$Lambda$3();

    private RealCheckoutShippingRatesInteractor$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutShippingRatesInteractor.lambda$execute$1((Checkout.ShippingRates)object);
    }
}

