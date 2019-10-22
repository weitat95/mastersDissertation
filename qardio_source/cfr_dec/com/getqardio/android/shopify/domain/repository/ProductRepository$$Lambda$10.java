/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ProductRepository$$Lambda$10
implements Storefront.CollectionQuery.ProductsArgumentsDefinition {
    private final String arg$1;

    private ProductRepository$$Lambda$10(String string2) {
        this.arg$1 = string2;
    }

    public static Storefront.CollectionQuery.ProductsArgumentsDefinition lambdaFactory$(String string2) {
        return new ProductRepository$$Lambda$10(string2);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CollectionQuery.ProductsArguments productsArguments) {
        ProductRepository.lambda$null$0(this.arg$1, productsArguments);
    }
}

