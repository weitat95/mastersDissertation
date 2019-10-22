/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import io.reactivex.functions.Function;
import java.lang.annotation.Annotation;
import java.lang.invoke.LambdaForm;

final class ConverterFactory$$Lambda$1
implements Function {
    private static final ConverterFactory$$Lambda$1 instance = new ConverterFactory$$Lambda$1();

    private ConverterFactory$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Annotation)object).annotationType();
    }
}

