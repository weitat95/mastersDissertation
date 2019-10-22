/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxUtil;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$5
implements CompletableTransformer {
    private static final RxUtil$$Lambda$5 instance = new RxUtil$$Lambda$5();

    private RxUtil$$Lambda$5() {
    }

    public static CompletableTransformer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public CompletableSource apply(Completable completable) {
        return RxUtil.lambda$applyCompletableSchedulers$4(completable);
    }
}

