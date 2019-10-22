/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.core.util.Function;
import java.lang.invoke.LambdaForm;
import java.math.BigDecimal;
import java.text.NumberFormat;

final class CartHeaderView$$Lambda$1
implements Function {
    private final NumberFormat arg$1;

    private CartHeaderView$$Lambda$1(NumberFormat numberFormat) {
        this.arg$1 = numberFormat;
    }

    public static Function lambdaFactory$(NumberFormat numberFormat) {
        return new CartHeaderView$$Lambda$1(numberFormat);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.format((BigDecimal)object);
    }
}

