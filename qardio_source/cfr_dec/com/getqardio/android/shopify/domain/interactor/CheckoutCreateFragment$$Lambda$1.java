/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CheckoutCreateFragment;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CheckoutCreateFragment$$Lambda$1
implements Storefront.CheckoutLineItemConnectionQueryDefinition {
    private static final CheckoutCreateFragment$$Lambda$1 instance = new CheckoutCreateFragment$$Lambda$1();

    private CheckoutCreateFragment$$Lambda$1() {
    }

    public static Storefront.CheckoutLineItemConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CheckoutLineItemConnectionQuery checkoutLineItemConnectionQuery) {
        CheckoutCreateFragment.lambda$define$3(checkoutLineItemConnectionQuery);
    }
}

