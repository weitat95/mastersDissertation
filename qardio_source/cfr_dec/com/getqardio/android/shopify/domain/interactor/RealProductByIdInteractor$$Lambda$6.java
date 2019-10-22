/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$6
implements Storefront.ProductVariantEdgeQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$6 instance = new RealProductByIdInteractor$$Lambda$6();

    private RealProductByIdInteractor$$Lambda$6() {
    }

    public static Storefront.ProductVariantEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        RealProductByIdInteractor.lambda$null$5(productVariantEdgeQuery);
    }
}

