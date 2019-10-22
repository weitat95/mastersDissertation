/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.product.RealProductViewModel;
import java.lang.invoke.LambdaForm;

final class RealProductViewModel$$Lambda$1
implements WeakObserver.OnNextDelegate {
    private static final RealProductViewModel$$Lambda$1 instance = new RealProductViewModel$$Lambda$1();

    private RealProductViewModel$$Lambda$1() {
    }

    public static WeakObserver.OnNextDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onNext(Object object, Object object2) {
        RealProductViewModel.access$lambda$0((RealProductViewModel)object, (ProductDetails)object2);
    }
}

