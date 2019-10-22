/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealShopSettingInteractor$$Lambda$1
implements Storefront.ShopQueryDefinition {
    private static final RealShopSettingInteractor$$Lambda$1 instance = new RealShopSettingInteractor$$Lambda$1();

    private RealShopSettingInteractor$$Lambda$1() {
    }

    public static Storefront.ShopQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ShopQuery shopQuery) {
        RealShopSettingInteractor.lambda$execute$1(shopQuery);
    }
}

