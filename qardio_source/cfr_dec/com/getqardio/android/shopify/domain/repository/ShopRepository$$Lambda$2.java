/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class ShopRepository$$Lambda$2
implements Function {
    private static final ShopRepository$$Lambda$2 instance = new ShopRepository$$Lambda$2();

    private ShopRepository$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.QueryRoot)object).getShop();
    }
}

