/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity;
import java.lang.invoke.LambdaForm;

final class ProductDetailsActivity$$Lambda$2
implements SwipeRefreshLayout.OnRefreshListener {
    private final ProductDetailsActivity arg$1;

    private ProductDetailsActivity$$Lambda$2(ProductDetailsActivity productDetailsActivity) {
        this.arg$1 = productDetailsActivity;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(ProductDetailsActivity productDetailsActivity) {
        return new ProductDetailsActivity$$Lambda$2(productDetailsActivity);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onCreate$1();
    }
}

