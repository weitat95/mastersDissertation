/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class ProductRepository$$Lambda$4
implements Function {
    private static final ProductRepository$$Lambda$4 instance = new ProductRepository$$Lambda$4();

    private ProductRepository$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.ProductConnection)object).getEdges();
    }
}

