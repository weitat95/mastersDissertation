/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import com.getqardio.android.shopify.util.WeakConsumer$$Lambda$1;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;

public class WeakConsumer<TARGET, RESPONSE> {
    AcceptDelegate<TARGET, RESPONSE> acceptDelegate;
    final WeakReference<TARGET> targetRef;

    private WeakConsumer(TARGET TARGET) {
        this.targetRef = new WeakReference<TARGET>(TARGET);
    }

    public static <TARGET, RESPONSE> WeakConsumer<TARGET, RESPONSE> forTarget(TARGET TARGET) {
        return new WeakConsumer<TARGET, RESPONSE>(TARGET);
    }

    public Consumer<RESPONSE> create() {
        return WeakConsumer$$Lambda$1.lambdaFactory$(this);
    }

    public WeakConsumer<TARGET, RESPONSE> delegateAccept(AcceptDelegate<TARGET, RESPONSE> acceptDelegate) {
        this.acceptDelegate = acceptDelegate;
        return this;
    }

    /* synthetic */ void lambda$create$0(Object object) throws Exception {
        Object t = this.targetRef.get();
        if (t != null && this.acceptDelegate != null) {
            this.acceptDelegate.accept(t, object);
        }
    }

    public static interface AcceptDelegate<TARGET, RESPONSE> {
        public void accept(TARGET var1, RESPONSE var2);
    }

}

