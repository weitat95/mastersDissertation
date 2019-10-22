/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealShopSettingInteractor$$Lambda$2
implements Function {
    private static final RealShopSettingInteractor$$Lambda$2 instance = new RealShopSettingInteractor$$Lambda$2();

    private RealShopSettingInteractor$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.convertToShopSettings((Storefront.Shop)object);
    }
}

