/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutFragment$$Lambda$2
implements Storefront.CheckoutLineItemEdgeQueryDefinition {
    private static final CheckoutFragment$$Lambda$2 instance = new CheckoutFragment$$Lambda$2();

    private CheckoutFragment$$Lambda$2() {
    }

    public static Storefront.CheckoutLineItemEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemEdgeQuery checkoutLineItemEdgeQuery) {
        CheckoutFragment.lambda$null$2(checkoutLineItemEdgeQuery);
    }
}

