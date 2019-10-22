/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutFragment$$Lambda$3
implements Storefront.CheckoutLineItemQueryDefinition {
    private static final CheckoutFragment$$Lambda$3 instance = new CheckoutFragment$$Lambda$3();

    private CheckoutFragment$$Lambda$3() {
    }

    public static Storefront.CheckoutLineItemQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemQuery checkoutLineItemQuery) {
        CheckoutFragment.lambda$null$1(checkoutLineItemQuery);
    }
}

