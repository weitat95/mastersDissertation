/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import com.jakewharton.retrofit2.adapter.rxjava2.BodyObservable;
import com.jakewharton.retrofit2.adapter.rxjava2.CallObservable;
import com.jakewharton.retrofit2.adapter.rxjava2.ResultObservable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

final class RxJava2CallAdapter
implements CallAdapter<Object> {
    private final boolean isBody;
    private final boolean isCompletable;
    private final boolean isFlowable;
    private final boolean isMaybe;
    private final boolean isResult;
    private final boolean isSingle;
    private final Type responseType;
    private final Scheduler scheduler;

    RxJava2CallAdapter(Type type, Scheduler scheduler, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.responseType = type;
        this.scheduler = scheduler;
        this.isResult = bl;
        this.isBody = bl2;
        this.isFlowable = bl3;
        this.isSingle = bl4;
        this.isMaybe = bl5;
        this.isCompletable = bl6;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <R> Object adapt(Call<R> object) {
        object = new CallObservable(object);
        if (this.isResult) {
            object = new ResultObservable(object);
        } else if (this.isBody) {
            object = new BodyObservable(object);
        }
        Observable observable = object;
        if (this.scheduler != null) {
            observable = ((Observable)object).subscribeOn(this.scheduler);
        }
        if (this.isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (this.isSingle) {
            return observable.singleOrError();
        }
        if (this.isMaybe) {
            return observable.singleElement();
        }
        object = observable;
        if (!this.isCompletable) return object;
        return observable.ignoreElements();
    }

    @Override
    public Type responseType() {
        return this.responseType;
    }
}

