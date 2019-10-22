/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.RxErrorHandlingCallAdapterFactory;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1
implements Function {
    private static final RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1 instance = new RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1();

    private RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxErrorHandlingCallAdapterFactory.RxCallAdapterWrapper.lambda$adapt$0(object);
    }
}

