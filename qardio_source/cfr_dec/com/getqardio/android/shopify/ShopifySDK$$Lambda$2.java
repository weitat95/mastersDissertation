/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ShopifySDK$$Lambda$2
implements Consumer {
    private final ShopifySDK arg$1;

    private ShopifySDK$$Lambda$2(ShopifySDK shopifySDK) {
        this.arg$1 = shopifySDK;
    }

    public static Consumer lambdaFactory$(ShopifySDK shopifySDK) {
        return new ShopifySDK$$Lambda$2(shopifySDK);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$fetchShopSettings$1((ShopSettings)object);
    }
}

