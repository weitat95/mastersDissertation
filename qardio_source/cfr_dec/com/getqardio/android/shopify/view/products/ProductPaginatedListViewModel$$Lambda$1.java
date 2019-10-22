/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import com.getqardio.android.shopify.view.products.ProductPaginatedListViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class ProductPaginatedListViewModel$$Lambda$1
implements ObservableTransformer {
    private final ProductPaginatedListViewModel arg$1;

    private ProductPaginatedListViewModel$$Lambda$1(ProductPaginatedListViewModel productPaginatedListViewModel) {
        this.arg$1 = productPaginatedListViewModel;
    }

    public static ObservableTransformer lambdaFactory$(ProductPaginatedListViewModel productPaginatedListViewModel) {
        return new ProductPaginatedListViewModel$$Lambda$1(productPaginatedListViewModel);
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return this.arg$1.lambda$nextPageRequestComposer$1(observable);
    }
}

