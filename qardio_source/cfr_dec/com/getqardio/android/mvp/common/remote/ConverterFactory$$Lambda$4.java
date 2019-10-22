/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.ConverterFactory;
import io.reactivex.functions.Predicate;
import java.lang.invoke.LambdaForm;

final class ConverterFactory$$Lambda$4
implements Predicate {
    private final Class arg$1;

    private ConverterFactory$$Lambda$4(Class class_) {
        this.arg$1 = class_;
    }

    public static Predicate lambdaFactory$(Class class_) {
        return new ConverterFactory$$Lambda$4(class_);
    }

    @LambdaForm.Hidden
    public boolean test(Object object) {
        return ConverterFactory.lambda$fieldMarkedWithAnnotation$1(this.arg$1, (Class)object);
    }
}

