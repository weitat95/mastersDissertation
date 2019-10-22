/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$23
implements Storefront.MutationQueryDefinition {
    private final String arg$1;
    private final Storefront.TokenizedPaymentInput arg$2;
    private final Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition arg$3;

    private CheckoutRepository$$Lambda$23(String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        this.arg$1 = string2;
        this.arg$2 = tokenizedPaymentInput;
        this.arg$3 = checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition;
    }

    public static Storefront.MutationQueryDefinition lambdaFactory$(String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$23(string2, tokenizedPaymentInput, checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.MutationQuery mutationQuery) {
        CheckoutRepository.lambda$complete$20(this.arg$1, this.arg$2, this.arg$3, mutationQuery);
    }
}

