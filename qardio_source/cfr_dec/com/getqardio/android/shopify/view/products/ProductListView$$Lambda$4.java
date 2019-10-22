/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.shopify.view.products.ProductListView;
import java.lang.invoke.LambdaForm;

final class ProductListView$$Lambda$4
implements SwipeRefreshLayout.OnRefreshListener {
    private final ProductListView arg$1;

    private ProductListView$$Lambda$4(ProductListView productListView) {
        this.arg$1 = productListView;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(ProductListView productListView) {
        return new ProductListView$$Lambda$4(productListView);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onFinishInflate$2();
    }
}

