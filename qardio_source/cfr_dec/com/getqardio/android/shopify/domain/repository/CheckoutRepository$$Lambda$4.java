/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$4
implements Storefront.MutationQueryDefinition {
    private final Storefront.MailingAddressInput arg$1;
    private final String arg$2;
    private final Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition arg$3;

    private CheckoutRepository$$Lambda$4(Storefront.MailingAddressInput mailingAddressInput, String string2, Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        this.arg$1 = mailingAddressInput;
        this.arg$2 = string2;
        this.arg$3 = checkoutShippingAddressUpdatePayloadQueryDefinition;
    }

    public static Storefront.MutationQueryDefinition lambdaFactory$(Storefront.MailingAddressInput mailingAddressInput, String string2, Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        return new CheckoutRepository$$Lambda$4(mailingAddressInput, string2, checkoutShippingAddressUpdatePayloadQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.MutationQuery mutationQuery) {
        CheckoutRepository.lambda$updateShippingAddress$3(this.arg$1, this.arg$2, this.arg$3, mutationQuery);
    }
}

