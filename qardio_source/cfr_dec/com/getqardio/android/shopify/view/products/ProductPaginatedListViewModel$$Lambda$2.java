/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import com.getqardio.android.shopify.view.products.ProductPaginatedListViewModel;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class ProductPaginatedListViewModel$$Lambda$2
implements Function {
    private final ProductPaginatedListViewModel arg$1;

    private ProductPaginatedListViewModel$$Lambda$2(ProductPaginatedListViewModel productPaginatedListViewModel) {
        this.arg$1 = productPaginatedListViewModel;
    }

    public static Function lambdaFactory$(ProductPaginatedListViewModel productPaginatedListViewModel) {
        return new ProductPaginatedListViewModel$$Lambda$2(productPaginatedListViewModel);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$0((String)object);
    }
}

