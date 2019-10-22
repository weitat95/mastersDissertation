/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.product.RealProductViewModel;
import java.lang.invoke.LambdaForm;

final class RealProductViewModel$$Lambda$2
implements WeakObserver.OnErrorDelegate {
    private static final RealProductViewModel$$Lambda$2 instance = new RealProductViewModel$$Lambda$2();

    private RealProductViewModel$$Lambda$2() {
    }

    public static WeakObserver.OnErrorDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealProductViewModel.access$lambda$1((RealProductViewModel)object, throwable);
    }
}

