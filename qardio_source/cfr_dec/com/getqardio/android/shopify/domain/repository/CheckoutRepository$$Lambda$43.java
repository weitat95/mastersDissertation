/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$43
implements Storefront.UserErrorQueryDefinition {
    private static final CheckoutRepository$$Lambda$43 instance = new CheckoutRepository$$Lambda$43();

    private CheckoutRepository$$Lambda$43() {
    }

    public static Storefront.UserErrorQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.UserErrorQuery userErrorQuery) {
        CheckoutRepository.lambda$null$1(userErrorQuery);
    }
}

