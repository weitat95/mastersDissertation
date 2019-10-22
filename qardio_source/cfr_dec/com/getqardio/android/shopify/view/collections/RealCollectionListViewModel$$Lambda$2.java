/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.view.collections.RealCollectionListViewModel;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCollectionListViewModel$$Lambda$2
implements Function {
    private final RealCollectionListViewModel arg$1;

    private RealCollectionListViewModel$$Lambda$2(RealCollectionListViewModel realCollectionListViewModel) {
        this.arg$1 = realCollectionListViewModel;
    }

    public static Function lambdaFactory$(RealCollectionListViewModel realCollectionListViewModel) {
        return new RealCollectionListViewModel$$Lambda$2(realCollectionListViewModel);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$0((String)object);
    }
}

