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

final class ProductDetailsActivity$$Lambda$3
implements View.OnClickListener {
    private final ProductDetailsActivity arg$1;
    private final String arg$2;

    private ProductDetailsActivity$$Lambda$3(ProductDetailsActivity productDetailsActivity, String string2) {
        this.arg$1 = productDetailsActivity;
        this.arg$2 = string2;
    }

    public static View.OnClickListener lambdaFactory$(ProductDetailsActivity productDetailsActivity, String string2) {
        return new ProductDetailsActivity$$Lambda$3(productDetailsActivity, string2);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreate$2(this.arg$2, view);
    }
}

