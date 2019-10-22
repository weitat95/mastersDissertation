/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.products.ProductListView;
import java.lang.invoke.LambdaForm;

final class ProductListView$$Lambda$2
implements Observer {
    private final ProductListView arg$1;

    private ProductListView$$Lambda$2(ProductListView productListView) {
        this.arg$1 = productListView;
    }

    public static Observer lambdaFactory$(ProductListView productListView) {
        return new ProductListView$$Lambda$2(productListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$1((UserErrorCallback.Error)object);
    }
}

