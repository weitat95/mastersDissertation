/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class RxUtil$$Lambda$1
implements ObservableTransformer {
    private static final RxUtil$$Lambda$1 instance = new RxUtil$$Lambda$1();

    private RxUtil$$Lambda$1() {
    }

    public static ObservableTransformer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return RxUtil.lambda$applySchedulers$0(observable);
    }
}

