/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCreateInteractor$$Lambda$2
implements Storefront.CheckoutCreatePayloadQueryDefinition {
    private static final RealCheckoutCreateInteractor$$Lambda$2 instance = new RealCheckoutCreateInteractor$$Lambda$2();

    private RealCheckoutCreateInteractor$$Lambda$2() {
    }

    public static Storefront.CheckoutCreatePayloadQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutCreatePayloadQuery checkoutCreatePayloadQuery) {
        RealCheckoutCreateInteractor.lambda$execute$1(checkoutCreatePayloadQuery);
    }
}

