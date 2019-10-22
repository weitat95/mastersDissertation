/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class RealCollectionNextPageInteractor$$Lambda$2
implements Function {
    private static final RealCollectionNextPageInteractor$$Lambda$2 instance = new RealCollectionNextPageInteractor$$Lambda$2();

    private RealCollectionNextPageInteractor$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.convertToCollections((List)object);
    }
}

