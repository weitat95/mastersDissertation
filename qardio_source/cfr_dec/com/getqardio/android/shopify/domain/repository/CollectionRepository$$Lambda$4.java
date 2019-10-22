/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class CollectionRepository$$Lambda$4
implements Function {
    private static final CollectionRepository$$Lambda$4 instance = new CollectionRepository$$Lambda$4();

    private CollectionRepository$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ((Storefront.CollectionConnection)object).getEdges();
    }
}

