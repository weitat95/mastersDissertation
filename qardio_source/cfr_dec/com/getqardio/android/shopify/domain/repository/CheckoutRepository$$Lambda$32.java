/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$32
implements Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition {
    private final Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition arg$1;

    private CheckoutRepository$$Lambda$32(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        this.arg$1 = checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition;
    }

    public static Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition lambdaFactory$(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$32(checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        CheckoutRepository.lambda$null$19(this.arg$1, checkoutCompleteWithTokenizedPaymentPayloadQuery);
    }
}

