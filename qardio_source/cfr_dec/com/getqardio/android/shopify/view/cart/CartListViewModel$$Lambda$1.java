/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.view.cart.CartListViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class CartListViewModel$$Lambda$1
implements ObservableTransformer {
    private final CartListViewModel arg$1;

    private CartListViewModel$$Lambda$1(CartListViewModel cartListViewModel) {
        this.arg$1 = cartListViewModel;
    }

    public static ObservableTransformer lambdaFactory$(CartListViewModel cartListViewModel) {
        return new CartListViewModel$$Lambda$1(cartListViewModel);
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return this.arg$1.lambda$nextPageRequestComposer$1(observable);
    }
}

