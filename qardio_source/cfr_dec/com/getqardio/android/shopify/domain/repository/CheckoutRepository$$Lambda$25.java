/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$25
implements Function {
    private static final CheckoutRepository$$Lambda$25 instance = new CheckoutRepository$$Lambda$25();

    private CheckoutRepository$$Lambda$25() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return CheckoutRepository.lambda$complete$21((Storefront.CheckoutCompleteWithTokenizedPaymentPayload)object);
    }
}

