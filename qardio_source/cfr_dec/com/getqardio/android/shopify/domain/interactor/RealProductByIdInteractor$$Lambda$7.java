/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$7
implements Storefront.ProductVariantQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$7 instance = new RealProductByIdInteractor$$Lambda$7();

    private RealProductByIdInteractor$$Lambda$7() {
    }

    public static Storefront.ProductVariantQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantQuery productVariantQuery) {
        RealProductByIdInteractor.lambda$null$4(productVariantQuery);
    }
}

