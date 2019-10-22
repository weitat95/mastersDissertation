/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.Function;
import com.getqardio.android.shopify.view.product.RealProductViewModel;
import java.lang.invoke.LambdaForm;

final class RealProductViewModel$$Lambda$3
implements Function {
    private static final RealProductViewModel$$Lambda$3 instance = new RealProductViewModel$$Lambda$3();

    private RealProductViewModel$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealProductViewModel.lambda$addToCart$0((ProductDetails.SelectedOption)object);
    }
}

