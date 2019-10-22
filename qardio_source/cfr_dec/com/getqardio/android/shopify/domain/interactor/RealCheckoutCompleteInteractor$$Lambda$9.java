/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$9
implements Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition {
    private static final RealCheckoutCompleteInteractor$$Lambda$9 instance = new RealCheckoutCompleteInteractor$$Lambda$9();

    private RealCheckoutCompleteInteractor$$Lambda$9() {
    }

    public static Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        RealCheckoutCompleteInteractor.lambda$null$1(checkoutCompleteWithTokenizedPaymentPayloadQuery);
    }
}

