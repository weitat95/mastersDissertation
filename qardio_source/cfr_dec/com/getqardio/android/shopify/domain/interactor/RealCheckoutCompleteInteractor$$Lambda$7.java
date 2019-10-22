/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$7
implements Function {
    private static final RealCheckoutCompleteInteractor$$Lambda$7 instance = new RealCheckoutCompleteInteractor$$Lambda$7();

    private RealCheckoutCompleteInteractor$$Lambda$7() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutCompleteInteractor.lambda$null$4((Storefront.Payment)object);
    }
}

