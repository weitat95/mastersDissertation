/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$5
implements Function {
    private static final RealCheckoutCompleteInteractor$$Lambda$5 instance = new RealCheckoutCompleteInteractor$$Lambda$5();

    private RealCheckoutCompleteInteractor$$Lambda$5() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutCompleteInteractor.lambda$execute$7((Throwable)object);
    }
}

