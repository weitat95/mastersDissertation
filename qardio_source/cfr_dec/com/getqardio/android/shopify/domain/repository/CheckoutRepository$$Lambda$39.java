/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$39
implements Storefront.UserErrorQueryDefinition {
    private static final CheckoutRepository$$Lambda$39 instance = new CheckoutRepository$$Lambda$39();

    private CheckoutRepository$$Lambda$39() {
    }

    public static Storefront.UserErrorQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.UserErrorQuery userErrorQuery) {
        CheckoutRepository.lambda$null$10(userErrorQuery);
    }
}

