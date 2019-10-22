/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import com.getqardio.android.shopify.util.Function;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class Converters$$Lambda$9
implements Function {
    private static final Converters$$Lambda$9 instance = new Converters$$Lambda$9();

    private Converters$$Lambda$9() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.lambda$null$9((Storefront.ProductEdge)object);
    }
}

