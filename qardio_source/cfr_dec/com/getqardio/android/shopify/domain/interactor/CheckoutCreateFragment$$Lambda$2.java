/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutCreateFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutCreateFragment$$Lambda$2
implements Storefront.CheckoutLineItemEdgeQueryDefinition {
    private static final CheckoutCreateFragment$$Lambda$2 instance = new CheckoutCreateFragment$$Lambda$2();

    private CheckoutCreateFragment$$Lambda$2() {
    }

    public static Storefront.CheckoutLineItemEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemEdgeQuery checkoutLineItemEdgeQuery) {
        CheckoutCreateFragment.lambda$null$2(checkoutLineItemEdgeQuery);
    }
}

