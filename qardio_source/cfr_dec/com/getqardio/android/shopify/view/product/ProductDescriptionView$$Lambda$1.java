/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.Function;
import com.getqardio.android.shopify.view.product.ProductDescriptionView;
import java.lang.invoke.LambdaForm;

final class ProductDescriptionView$$Lambda$1
implements Function {
    private static final ProductDescriptionView$$Lambda$1 instance = new ProductDescriptionView$$Lambda$1();

    private ProductDescriptionView$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ProductDescriptionView.lambda$formatMinPrice$0((ProductDetails.Variant)object);
    }
}

