/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$1
implements Storefront.CheckoutEmailUpdatePayloadQueryDefinition {
    private static final RealCheckoutCompleteInteractor$$Lambda$1 instance = new RealCheckoutCompleteInteractor$$Lambda$1();

    private RealCheckoutCompleteInteractor$$Lambda$1() {
    }

    public static Storefront.CheckoutEmailUpdatePayloadQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        RealCheckoutCompleteInteractor.lambda$execute$0(checkoutEmailUpdatePayloadQuery);
    }
}

