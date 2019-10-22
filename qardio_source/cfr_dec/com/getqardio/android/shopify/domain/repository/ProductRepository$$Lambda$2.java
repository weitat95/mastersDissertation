/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$2
implements Function {
    private static final ProductRepository$$Lambda$2 instance = new ProductRepository$$Lambda$2();

    private ProductRepository$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ProductRepository.lambda$nextPage$4((Storefront.QueryRoot)object);
    }
}

