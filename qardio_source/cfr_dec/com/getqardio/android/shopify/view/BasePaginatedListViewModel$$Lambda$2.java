/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import com.getqardio.android.shopify.util.WeakConsumer;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import java.lang.invoke.LambdaForm;

final class BasePaginatedListViewModel$$Lambda$2
implements WeakConsumer.AcceptDelegate {
    private static final BasePaginatedListViewModel$$Lambda$2 instance = new BasePaginatedListViewModel$$Lambda$2();

    private BasePaginatedListViewModel$$Lambda$2() {
    }

    public static WeakConsumer.AcceptDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object, Object object2) {
        ((BasePaginatedListViewModel)object).onNextPageError((Throwable)object2);
    }
}

