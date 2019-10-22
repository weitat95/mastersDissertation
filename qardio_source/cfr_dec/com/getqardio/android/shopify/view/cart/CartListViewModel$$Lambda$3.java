/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.model.CartItem;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class CartListViewModel$$Lambda$3
implements Function {
    private static final CartListViewModel$$Lambda$3 instance = new CartListViewModel$$Lambda$3();

    private CartListViewModel$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Cart)object).cartItems();
    }
}

