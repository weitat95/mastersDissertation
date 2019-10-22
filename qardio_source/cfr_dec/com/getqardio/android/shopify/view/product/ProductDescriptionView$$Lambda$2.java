/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import java.lang.invoke.LambdaForm;
import java.math.BigDecimal;
import java.util.Comparator;

final class ProductDescriptionView$$Lambda$2
implements Comparator {
    private static final ProductDescriptionView$$Lambda$2 instance = new ProductDescriptionView$$Lambda$2();

    private ProductDescriptionView$$Lambda$2() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public int compare(Object object, Object object2) {
        return ((BigDecimal)object).compareTo((BigDecimal)object2);
    }
}

