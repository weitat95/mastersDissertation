/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$6
implements Function {
    private static final ProductRepository$$Lambda$6 instance = new ProductRepository$$Lambda$6();

    private ProductRepository$$Lambda$6() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ProductRepository.lambda$product$7((Storefront.QueryRoot)object);
    }
}

