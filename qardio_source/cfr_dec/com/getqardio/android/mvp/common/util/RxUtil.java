/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.util;

import com.getqardio.android.mvp.common.util.RxUtil$$Lambda$1;
import com.getqardio.android.mvp.common.util.RxUtil$$Lambda$2;
import com.getqardio.android.mvp.common.util.RxUtil$$Lambda$3;
import com.getqardio.android.mvp.common.util.RxUtil$$Lambda$5;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    public static CompletableTransformer applyCompletableSchedulers() {
        return RxUtil$$Lambda$5.lambdaFactory$();
    }

    public static <T> MaybeTransformer<T, T> applyMaybeSchedulers() {
        return RxUtil$$Lambda$2.lambdaFactory$();
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return RxUtil$$Lambda$1.lambdaFactory$();
    }

    public static <T> SingleTransformer<T, T> applySingleSchedulers() {
        return RxUtil$$Lambda$3.lambdaFactory$();
    }

    static /* synthetic */ CompletableSource lambda$applyCompletableSchedulers$4(Completable completable) {
        return completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    static /* synthetic */ MaybeSource lambda$applyMaybeSchedulers$1(Maybe maybe) {
        return maybe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    static /* synthetic */ ObservableSource lambda$applySchedulers$0(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    static /* synthetic */ SingleSource lambda$applySingleSchedulers$2(Single single) {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static void unsubscribe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

