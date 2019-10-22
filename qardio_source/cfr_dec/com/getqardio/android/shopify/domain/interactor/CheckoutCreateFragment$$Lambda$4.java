/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutCreateFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutCreateFragment$$Lambda$4
implements Storefront.ProductVariantQueryDefinition {
    private static final CheckoutCreateFragment$$Lambda$4 instance = new CheckoutCreateFragment$$Lambda$4();

    private CheckoutCreateFragment$$Lambda$4() {
    }

    public static Storefront.ProductVariantQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantQuery productVariantQuery) {
        CheckoutCreateFragment.lambda$null$0(productVariantQuery);
    }
}

