/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import com.getqardio.android.shopify.view.products.RealProductListViewModel;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealProductListViewModel$$Lambda$2
implements Function {
    private final RealProductListViewModel arg$1;

    private RealProductListViewModel$$Lambda$2(RealProductListViewModel realProductListViewModel) {
        this.arg$1 = realProductListViewModel;
    }

    public static Function lambdaFactory$(RealProductListViewModel realProductListViewModel) {
        return new RealProductListViewModel$$Lambda$2(realProductListViewModel);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$0((String)object);
    }
}

