/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$8
implements Predicate {
    private static final RealCheckoutCompleteInteractor$$Lambda$8 instance = new RealCheckoutCompleteInteractor$$Lambda$8();

    private RealCheckoutCompleteInteractor$$Lambda$8() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return RealCheckoutCompleteInteractor.lambda$null$5((Throwable)object);
    }
}

