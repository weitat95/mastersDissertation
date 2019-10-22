/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$3
implements Function {
    private final RealCheckoutCompleteInteractor arg$1;

    private RealCheckoutCompleteInteractor$$Lambda$3(RealCheckoutCompleteInteractor realCheckoutCompleteInteractor) {
        this.arg$1 = realCheckoutCompleteInteractor;
    }

    public static Function lambdaFactory$(RealCheckoutCompleteInteractor realCheckoutCompleteInteractor) {
        return new RealCheckoutCompleteInteractor$$Lambda$3(realCheckoutCompleteInteractor);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$execute$6((Storefront.Payment)object);
    }
}

