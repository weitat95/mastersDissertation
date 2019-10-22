/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class BasePaginatedListViewModel$$Lambda$3
implements WeakObserver.OnNextDelegate {
    private static final BasePaginatedListViewModel$$Lambda$3 instance = new BasePaginatedListViewModel$$Lambda$3();

    private BasePaginatedListViewModel$$Lambda$3() {
    }

    public static WeakObserver.OnNextDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onNext(Object object, Object object2) {
        ((BasePaginatedListViewModel)object).onNextPageResponseInternal((List)object2);
    }
}

