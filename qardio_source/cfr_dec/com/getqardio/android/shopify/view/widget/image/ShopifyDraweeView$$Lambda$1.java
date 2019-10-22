/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.widget.image;

import com.facebook.imagepipeline.request.ImageRequest;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;
import java.lang.invoke.LambdaForm;

final class ShopifyDraweeView$$Lambda$1
implements Runnable {
    private final ShopifyDraweeView arg$1;
    private final ImageRequest arg$2;

    private ShopifyDraweeView$$Lambda$1(ShopifyDraweeView shopifyDraweeView, ImageRequest imageRequest) {
        this.arg$1 = shopifyDraweeView;
        this.arg$2 = imageRequest;
    }

    public static Runnable lambdaFactory$(ShopifyDraweeView shopifyDraweeView, ImageRequest imageRequest) {
        return new ShopifyDraweeView$$Lambda$1(shopifyDraweeView, imageRequest);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$performLoadShopifyImage$0(this.arg$2);
    }
}

