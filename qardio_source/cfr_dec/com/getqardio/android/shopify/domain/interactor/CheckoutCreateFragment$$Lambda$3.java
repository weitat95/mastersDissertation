/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutCreateFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutCreateFragment$$Lambda$3
implements Storefront.CheckoutLineItemQueryDefinition {
    private static final CheckoutCreateFragment$$Lambda$3 instance = new CheckoutCreateFragment$$Lambda$3();

    private CheckoutCreateFragment$$Lambda$3() {
    }

    public static Storefront.CheckoutLineItemQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemQuery checkoutLineItemQuery) {
        CheckoutCreateFragment.lambda$null$1(checkoutLineItemQuery);
    }
}

