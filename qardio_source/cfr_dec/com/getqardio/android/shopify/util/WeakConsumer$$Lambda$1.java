/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.WeakConsumer;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class WeakConsumer$$Lambda$1
implements Consumer {
    private final WeakConsumer arg$1;

    private WeakConsumer$$Lambda$1(WeakConsumer weakConsumer) {
        this.arg$1 = weakConsumer;
    }

    public static Consumer lambdaFactory$(WeakConsumer weakConsumer) {
        return new WeakConsumer$$Lambda$1(weakConsumer);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$create$0(object);
    }
}

