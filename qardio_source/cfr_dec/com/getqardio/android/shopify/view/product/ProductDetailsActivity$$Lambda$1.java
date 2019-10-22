/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.product;

import android.view.View;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity;
import java.lang.invoke.LambdaForm;

final class ProductDetailsActivity$$Lambda$1
implements View.OnClickListener {
    private final ProductDetailsActivity arg$1;

    private ProductDetailsActivity$$Lambda$1(ProductDetailsActivity productDetailsActivity) {
        this.arg$1 = productDetailsActivity;
    }

    public static View.OnClickListener lambdaFactory$(ProductDetailsActivity productDetailsActivity) {
        return new ProductDetailsActivity$$Lambda$1(productDetailsActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateOptionsMenu$0(view);
    }
}

