/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.view.cart.CartListViewModel;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CartListViewModel$$Lambda$2
implements Function {
    private final CartListViewModel arg$1;

    private CartListViewModel$$Lambda$2(CartListViewModel cartListViewModel) {
        this.arg$1 = cartListViewModel;
    }

    public static Function lambdaFactory$(CartListViewModel cartListViewModel) {
        return new CartListViewModel$$Lambda$2(cartListViewModel);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$0((String)object);
    }
}

