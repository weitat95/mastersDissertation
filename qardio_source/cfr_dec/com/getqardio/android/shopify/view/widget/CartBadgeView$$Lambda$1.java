/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.widget;

import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.widget.CartBadgeView;
import java.lang.invoke.LambdaForm;

final class CartBadgeView$$Lambda$1
implements WeakObserver.OnNextDelegate {
    private static final CartBadgeView$$Lambda$1 instance = new CartBadgeView$$Lambda$1();

    private CartBadgeView$$Lambda$1() {
    }

    public static WeakObserver.OnNextDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onNext(Object object, Object object2) {
        CartBadgeView.access$lambda$0((CartBadgeView)((Object)object), (Cart)object2);
    }
}

