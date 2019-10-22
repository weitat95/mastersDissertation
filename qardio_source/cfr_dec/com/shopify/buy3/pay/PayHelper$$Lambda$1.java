/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.pay;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.shopify.buy3.pay.PayHelper;

final class PayHelper$$Lambda$1
implements ResultCallback {
    private final PayHelper.AndroidPayReadyCallback arg$1;

    private PayHelper$$Lambda$1(PayHelper.AndroidPayReadyCallback androidPayReadyCallback) {
        this.arg$1 = androidPayReadyCallback;
    }

    public static ResultCallback lambdaFactory$(PayHelper.AndroidPayReadyCallback androidPayReadyCallback) {
        return new PayHelper$$Lambda$1(androidPayReadyCallback);
    }

    public void onResult(Result result) {
        PayHelper.lambda$isReadyToPay$0(this.arg$1, (BooleanResult)result);
    }
}

