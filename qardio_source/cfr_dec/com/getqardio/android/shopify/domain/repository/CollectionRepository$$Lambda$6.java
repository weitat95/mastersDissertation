/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CollectionRepository;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class CollectionRepository$$Lambda$6
implements Storefront.ShopQuery.CollectionsArgumentsDefinition {
    private final String arg$1;

    private CollectionRepository$$Lambda$6(String string2) {
        this.arg$1 = string2;
    }

    public static Storefront.ShopQuery.CollectionsArgumentsDefinition lambdaFactory$(String string2) {
        return new CollectionRepository$$Lambda$6(string2);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ShopQuery.CollectionsArguments collectionsArguments) {
        CollectionRepository.lambda$null$0(this.arg$1, collectionsArguments);
    }
}

