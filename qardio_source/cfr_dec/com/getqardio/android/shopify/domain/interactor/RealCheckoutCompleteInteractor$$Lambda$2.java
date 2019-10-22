/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$2
implements Function {
    private final RealCheckoutCompleteInteractor arg$1;
    private final String arg$2;
    private final Storefront.TokenizedPaymentInput arg$3;

    private RealCheckoutCompleteInteractor$$Lambda$2(RealCheckoutCompleteInteractor realCheckoutCompleteInteractor, String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput) {
        this.arg$1 = realCheckoutCompleteInteractor;
        this.arg$2 = string2;
        this.arg$3 = tokenizedPaymentInput;
    }

    public static Function lambdaFactory$(RealCheckoutCompleteInteractor realCheckoutCompleteInteractor, String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput) {
        return new RealCheckoutCompleteInteractor$$Lambda$2(realCheckoutCompleteInteractor, string2, tokenizedPaymentInput);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$execute$2(this.arg$2, this.arg$3, (Storefront.Checkout)object);
    }
}

