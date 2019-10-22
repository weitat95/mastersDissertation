/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import java.lang.invoke.LambdaForm;
import java.math.BigDecimal;
import java.util.Comparator;

final class Converters$$Lambda$16
implements Comparator {
    private static final Converters$$Lambda$16 instance = new Converters$$Lambda$16();

    private Converters$$Lambda$16() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public int compare(Object object, Object object2) {
        return ((BigDecimal)object).compareTo((BigDecimal)object2);
    }
}

