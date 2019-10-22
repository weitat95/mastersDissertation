/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutFragment$$Lambda$4
implements Storefront.ProductVariantQueryDefinition {
    private static final CheckoutFragment$$Lambda$4 instance = new CheckoutFragment$$Lambda$4();

    private CheckoutFragment$$Lambda$4() {
    }

    public static Storefront.ProductVariantQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantQuery productVariantQuery) {
        CheckoutFragment.lambda$null$0(productVariantQuery);
    }
}

