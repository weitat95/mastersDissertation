/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify;

import com.getqardio.android.shopify.RxUtil;
import com.shopify.buy3.GraphCall;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$2
implements SingleOnSubscribe {
    private final GraphCall arg$1;

    private RxUtil$$Lambda$2(GraphCall graphCall) {
        this.arg$1 = graphCall;
    }

    public static SingleOnSubscribe lambdaFactory$(GraphCall graphCall) {
        return new RxUtil$$Lambda$2(graphCall);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxUtil.lambda$rxGraphMutationCall$1(this.arg$1, singleEmitter);
    }
}

