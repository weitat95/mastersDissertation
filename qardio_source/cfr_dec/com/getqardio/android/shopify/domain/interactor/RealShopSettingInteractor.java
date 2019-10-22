/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealShopSettingInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.ShopSettingInteractor;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import com.getqardio.android.shopify.domain.repository.ShopRepository;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public final class RealShopSettingInteractor
implements ShopSettingInteractor {
    private final ShopRepository repository = new ShopRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$1(Storefront.ShopQuery shopQuery) {
        shopQuery.name().paymentSettings(RealShopSettingInteractor$$Lambda$3.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$0(Storefront.PaymentSettingsQuery paymentSettingsQuery) {
        paymentSettingsQuery.countryCode().acceptedCardBrands();
    }

    @Override
    public Single<ShopSettings> execute() {
        Storefront.ShopQueryDefinition shopQueryDefinition = RealShopSettingInteractor$$Lambda$1.lambdaFactory$();
        return this.repository.shopSettings(shopQueryDefinition).map(RealShopSettingInteractor$$Lambda$2.lambdaFactory$());
    }
}

