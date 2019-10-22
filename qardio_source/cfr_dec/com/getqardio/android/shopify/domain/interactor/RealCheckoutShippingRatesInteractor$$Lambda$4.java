/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class RealCheckoutShippingRatesInteractor$$Lambda$4
implements Predicate {
    private static final RealCheckoutShippingRatesInteractor$$Lambda$4 instance = new RealCheckoutShippingRatesInteractor$$Lambda$4();

    private RealCheckoutShippingRatesInteractor$$Lambda$4() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return RealCheckoutShippingRatesInteractor.lambda$execute$2((Throwable)object);
    }
}

