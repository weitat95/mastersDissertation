/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class RxEventBus$$Lambda$1
implements Predicate {
    private final Class arg$1;

    private RxEventBus$$Lambda$1(Class class_) {
        this.arg$1 = class_;
    }

    public static Predicate lambdaFactory$(Class class_) {
        return new RxEventBus$$Lambda$1(class_);
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return this.arg$1.isInstance(object);
    }
}

