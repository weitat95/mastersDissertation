/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CollectionRepository$$Lambda$3
implements Function {
    private static final CollectionRepository$$Lambda$3 instance = new CollectionRepository$$Lambda$3();

    private CollectionRepository$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.Shop)object).getCollections();
    }
}

