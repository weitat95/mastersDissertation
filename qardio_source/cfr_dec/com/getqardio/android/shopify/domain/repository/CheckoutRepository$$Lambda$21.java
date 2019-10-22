/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CheckoutRepository$$Lambda$21
implements Function {
    private static final CheckoutRepository$$Lambda$21 instance = new CheckoutRepository$$Lambda$21();

    private CheckoutRepository$$Lambda$21() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return CheckoutRepository.lambda$updateEmail$17((Storefront.CheckoutEmailUpdatePayload)object);
    }
}

