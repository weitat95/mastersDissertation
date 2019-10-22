/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCreateInteractor$$Lambda$4
implements Function {
    private static final RealCheckoutCreateInteractor$$Lambda$4 instance = new RealCheckoutCreateInteractor$$Lambda$4();

    private RealCheckoutCreateInteractor$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutCreateInteractor.lambda$execute$2((Throwable)object);
    }
}

