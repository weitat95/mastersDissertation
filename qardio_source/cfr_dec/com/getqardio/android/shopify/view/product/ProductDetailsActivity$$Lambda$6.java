/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity;
import java.lang.invoke.LambdaForm;

final class ProductDetailsActivity$$Lambda$6
implements Observer {
    private final ProductDetailsActivity arg$1;

    private ProductDetailsActivity$$Lambda$6(ProductDetailsActivity productDetailsActivity) {
        this.arg$1 = productDetailsActivity;
    }

    public static Observer lambdaFactory$(ProductDetailsActivity productDetailsActivity) {
        return new ProductDetailsActivity$$Lambda$6(productDetailsActivity);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$initViewModels$4((UserErrorCallback.Error)object);
    }
}

