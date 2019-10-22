/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutFragment$$Lambda$1
implements Storefront.CheckoutLineItemConnectionQueryDefinition {
    private static final CheckoutFragment$$Lambda$1 instance = new CheckoutFragment$$Lambda$1();

    private CheckoutFragment$$Lambda$1() {
    }

    public static Storefront.CheckoutLineItemConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemConnectionQuery checkoutLineItemConnectionQuery) {
        CheckoutFragment.lambda$define$3(checkoutLineItemConnectionQuery);
    }
}

