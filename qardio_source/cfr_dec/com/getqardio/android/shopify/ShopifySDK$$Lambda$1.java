/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class ShopifySDK$$Lambda$1
implements Function {
    private final ShopifySDK arg$1;

    private ShopifySDK$$Lambda$1(ShopifySDK shopifySDK) {
        this.arg$1 = shopifySDK;
    }

    public static Function lambdaFactory$(ShopifySDK shopifySDK) {
        return new ShopifySDK$$Lambda$1(shopifySDK);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$fetchShopSettings$0((Throwable)object);
    }
}

