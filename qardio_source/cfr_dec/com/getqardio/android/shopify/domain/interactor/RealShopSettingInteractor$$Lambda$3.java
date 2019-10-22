/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealShopSettingInteractor$$Lambda$3
implements Storefront.PaymentSettingsQueryDefinition {
    private static final RealShopSettingInteractor$$Lambda$3 instance = new RealShopSettingInteractor$$Lambda$3();

    private RealShopSettingInteractor$$Lambda$3() {
    }

    public static Storefront.PaymentSettingsQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.PaymentSettingsQuery paymentSettingsQuery) {
        RealShopSettingInteractor.lambda$null$0(paymentSettingsQuery);
    }
}

