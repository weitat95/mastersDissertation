/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.ShopRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class ShopRepository$$Lambda$1
implements Storefront.QueryRootQueryDefinition {
    private final Storefront.ShopQueryDefinition arg$1;

    private ShopRepository$$Lambda$1(Storefront.ShopQueryDefinition shopQueryDefinition) {
        this.arg$1 = shopQueryDefinition;
    }

    public static Storefront.QueryRootQueryDefinition lambdaFactory$(Storefront.ShopQueryDefinition shopQueryDefinition) {
        return new ShopRepository$$Lambda$1(shopQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.QueryRootQuery queryRootQuery) {
        ShopRepository.lambda$shopSettings$0(this.arg$1, queryRootQuery);
    }
}

