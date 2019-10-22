/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxEventBus;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxEventBus$$Lambda$2
implements Function {
    private static final RxEventBus$$Lambda$2 instance = new RxEventBus$$Lambda$2();

    private RxEventBus$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxEventBus.lambda$filteredObservable$0(object);
    }
}

