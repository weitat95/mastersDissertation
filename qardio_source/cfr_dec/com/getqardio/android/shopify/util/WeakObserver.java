/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import io.reactivex.observers.DisposableObserver;
import java.lang.ref.WeakReference;

public final class WeakObserver<TARGET, RESPONSE> {
    OnCompleteDelegate<TARGET> onCompleteDelegate;
    OnErrorDelegate<TARGET> onErrorDelegate;
    OnNextDelegate<TARGET, ? super RESPONSE> onNextDelegate;
    final WeakReference<TARGET> targetRef;

    public WeakObserver(TARGET TARGET) {
        this.targetRef = new WeakReference<TARGET>(TARGET);
    }

    public static <TARGET, RESPONSE> WeakObserver<TARGET, RESPONSE> forTarget(TARGET TARGET) {
        return new WeakObserver<TARGET, RESPONSE>(TARGET);
    }

    public DisposableObserver<? super RESPONSE> create() {
        return new DisposableObserver<RESPONSE>(){

            @Override
            public void onComplete() {
                Object t = WeakObserver.this.targetRef.get();
                if (t != null && WeakObserver.this.onCompleteDelegate != null) {
                    WeakObserver.this.onCompleteDelegate.onComplete(t);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Object t = WeakObserver.this.targetRef.get();
                if (t != null && WeakObserver.this.onErrorDelegate != null) {
                    WeakObserver.this.onErrorDelegate.onError(t, throwable);
                }
            }

            @Override
            public void onNext(RESPONSE RESPONSE) {
                Object t = WeakObserver.this.targetRef.get();
                if (t != null && WeakObserver.this.onNextDelegate != null) {
                    WeakObserver.this.onNextDelegate.onNext(t, RESPONSE);
                }
            }
        };
    }

    public WeakObserver<TARGET, RESPONSE> delegateOnCompleteDelegate(OnCompleteDelegate<TARGET> onCompleteDelegate) {
        this.onCompleteDelegate = onCompleteDelegate;
        return this;
    }

    public WeakObserver<TARGET, RESPONSE> delegateOnError(OnErrorDelegate<TARGET> onErrorDelegate) {
        this.onErrorDelegate = onErrorDelegate;
        return this;
    }

    public WeakObserver<TARGET, ? super RESPONSE> delegateOnNext(OnNextDelegate<TARGET, ? super RESPONSE> onNextDelegate) {
        this.onNextDelegate = onNextDelegate;
        return this;
    }

    public static interface OnCompleteDelegate<TARGET> {
        public void onComplete(TARGET var1);
    }

    public static interface OnErrorDelegate<TARGET> {
        public void onError(TARGET var1, Throwable var2);
    }

    public static interface OnNextDelegate<TARGET, RESPONSE> {
        public void onNext(TARGET var1, RESPONSE var2);
    }

}

