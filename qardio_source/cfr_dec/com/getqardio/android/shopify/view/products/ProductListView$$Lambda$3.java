/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.products.ProductListView;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class ProductListView$$Lambda$3
implements Observer {
    private final ProductListView arg$1;

    private ProductListView$$Lambda$3(ProductListView productListView) {
        this.arg$1 = productListView;
    }

    public static Observer lambdaFactory$(ProductListView productListView) {
        return new ProductListView$$Lambda$3(productListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        ProductListView.access$lambda$0(this.arg$1, (List)object);
    }
}

