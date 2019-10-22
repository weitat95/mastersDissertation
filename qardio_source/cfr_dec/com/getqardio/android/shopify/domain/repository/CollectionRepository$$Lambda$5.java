/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CollectionRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CollectionRepository$$Lambda$5
implements Storefront.ShopQueryDefinition {
    private final int arg$1;
    private final String arg$2;
    private final Storefront.CollectionConnectionQueryDefinition arg$3;

    private CollectionRepository$$Lambda$5(int n, String string2, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition) {
        this.arg$1 = n;
        this.arg$2 = string2;
        this.arg$3 = collectionConnectionQueryDefinition;
    }

    public static Storefront.ShopQueryDefinition lambdaFactory$(int n, String string2, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition) {
        return new CollectionRepository$$Lambda$5(n, string2, collectionConnectionQueryDefinition);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ShopQuery shopQuery) {
        CollectionRepository.lambda$null$1(this.arg$1, this.arg$2, this.arg$3, shopQuery);
    }
}

