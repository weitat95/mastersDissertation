/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxUtil;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$2
implements MaybeTransformer {
    private static final RxUtil$$Lambda$2 instance = new RxUtil$$Lambda$2();

    private RxUtil$$Lambda$2() {
    }

    public static MaybeTransformer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public MaybeSource apply(Maybe maybe) {
        return RxUtil.lambda$applyMaybeSchedulers$1(maybe);
    }
}

