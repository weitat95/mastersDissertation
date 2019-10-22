/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import com.getqardio.android.shopify.view.products.RealProductListViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class RealProductListViewModel$$Lambda$1
implements ObservableTransformer {
    private final RealProductListViewModel arg$1;

    private RealProductListViewModel$$Lambda$1(RealProductListViewModel realProductListViewModel) {
        this.arg$1 = realProductListViewModel;
    }

    public static ObservableTransformer lambdaFactory$(RealProductListViewModel realProductListViewModel) {
        return new RealProductListViewModel$$Lambda$1(realProductListViewModel);
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return this.arg$1.lambda$nextPageRequestComposer$1(observable);
    }
}

