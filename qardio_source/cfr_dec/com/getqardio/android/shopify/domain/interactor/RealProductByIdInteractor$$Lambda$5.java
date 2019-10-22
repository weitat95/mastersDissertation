/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$5
implements Storefront.ProductVariantConnectionQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$5 instance = new RealProductByIdInteractor$$Lambda$5();

    private RealProductByIdInteractor$$Lambda$5() {
    }

    public static Storefront.ProductVariantConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        RealProductByIdInteractor.lambda$null$6(productVariantConnectionQuery);
    }
}

