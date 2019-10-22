/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import io.reactivex.observers.DisposableSingleObserver;
import java.lang.ref.WeakReference;

public class WeakSingleObserver<TARGET, RESPONSE> {
    OnErrorDelegate<TARGET> onErrorDelegate;
    OnSuccessDelegate<TARGET, ? super RESPONSE> onNextDelegate;
    final WeakReference<TARGET> targetRef;

    public WeakSingleObserver(TARGET TARGET) {
        this.targetRef = new WeakReference<TARGET>(TARGET);
    }

    public static <TARGET, RESPONSE> WeakSingleObserver<TARGET, RESPONSE> forTarget(TARGET TARGET) {
        return new WeakSingleObserver<TARGET, RESPONSE>(TARGET);
    }

    public DisposableSingleObserver<? super RESPONSE> create() {
        return new DisposableSingleObserver<RESPONSE>(){

            @Override
            public void onError(Throwable throwable) {
                Object t = WeakSingleObserver.this.targetRef.get();
                if (t != null && WeakSingleObserver.this.onErrorDelegate != null) {
                    WeakSingleObserver.this.onErrorDelegate.onError(t, throwable);
                }
            }

            @Override
            public void onSuccess(RESPONSE RESPONSE) {
                Object t = WeakSingleObserver.this.targetRef.get();
                if (t != null && WeakSingleObserver.this.onNextDelegate != null) {
                    WeakSingleObserver.this.onNextDelegate.onSuccess(t, RESPONSE);
                }
            }
        };
    }

    public WeakSingleObserver<TARGET, RESPONSE> delegateOnError(OnErrorDelegate<TARGET> onErrorDelegate) {
        this.onErrorDelegate = onErrorDelegate;
        return this;
    }

    public WeakSingleObserver<TARGET, ? super RESPONSE> delegateOnSuccess(OnSuccessDelegate<TARGET, ? super RESPONSE> onSuccessDelegate) {
        this.onNextDelegate = onSuccessDelegate;
        return this;
    }

    public static interface OnErrorDelegate<TARGET> {
        public void onError(TARGET var1, Throwable var2);
    }

    public static interface OnSuccessDelegate<TARGET, RESPONSE> {
        public void onSuccess(TARGET var1, RESPONSE var2);
    }

}

