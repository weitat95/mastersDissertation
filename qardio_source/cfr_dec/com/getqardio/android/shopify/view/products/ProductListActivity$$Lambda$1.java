/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.products;

import android.view.View;
import com.getqardio.android.shopify.view.products.ProductListActivity;
import java.lang.invoke.LambdaForm;

final class ProductListActivity$$Lambda$1
implements View.OnClickListener {
    private final ProductListActivity arg$1;

    private ProductListActivity$$Lambda$1(ProductListActivity productListActivity) {
        this.arg$1 = productListActivity;
    }

    public static View.OnClickListener lambdaFactory$(ProductListActivity productListActivity) {
        return new ProductListActivity$$Lambda$1(productListActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateOptionsMenu$0(view);
    }
}

