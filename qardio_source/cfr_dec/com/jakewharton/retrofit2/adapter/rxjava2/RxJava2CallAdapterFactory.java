/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.retrofit2.adapter.rxjava2;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapter;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RxJava2CallAdapterFactory
extends CallAdapter.Factory {
    private final Scheduler scheduler;

    private RxJava2CallAdapterFactory(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public static RxJava2CallAdapterFactory create() {
        return new RxJava2CallAdapterFactory(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public CallAdapter<?> get(Type object, Annotation[] object2, Retrofit retrofit) {
        Class<?> class_ = RxJava2CallAdapterFactory.getRawType((Type)object);
        if (class_ == Completable.class) {
            return new RxJava2CallAdapter((Type)((Object)Void.class), this.scheduler, false, true, false, false, false, true);
        }
        boolean bl = class_ == Flowable.class;
        boolean bl2 = class_ == Single.class;
        boolean bl3 = class_ == Maybe.class;
        if (!(class_ == Observable.class || bl || bl2 || bl3)) {
            return null;
        }
        boolean bl4 = false;
        boolean bl5 = false;
        if (!(object instanceof ParameterizedType)) {
            if (bl) {
                object = "Flowable";
                throw new IllegalStateException((String)object + " return type must be parameterized as " + (String)object + "<Foo> or " + (String)object + "<? extends Foo>");
            }
            if (bl2) {
                object = "Single";
                throw new IllegalStateException((String)object + " return type must be parameterized as " + (String)object + "<Foo> or " + (String)object + "<? extends Foo>");
            }
            object = "Observable";
            throw new IllegalStateException((String)object + " return type must be parameterized as " + (String)object + "<Foo> or " + (String)object + "<? extends Foo>");
        }
        Class<?> class_2 = RxJava2CallAdapterFactory.getRawType((Type)(object = RxJava2CallAdapterFactory.getParameterUpperBound(0, (ParameterizedType)object)));
        if (class_2 == Response.class) {
            if (!(object instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
            object = RxJava2CallAdapterFactory.getParameterUpperBound(0, (ParameterizedType)object);
            return new RxJava2CallAdapter((Type)object, this.scheduler, bl4, bl5, bl, bl2, bl3, false);
        }
        if (class_2 != Result.class) {
            bl5 = true;
            return new RxJava2CallAdapter((Type)object, this.scheduler, bl4, bl5, bl, bl2, bl3, false);
        }
        if (!(object instanceof ParameterizedType)) {
            throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
        }
        object = RxJava2CallAdapterFactory.getParameterUpperBound(0, (ParameterizedType)object);
        bl4 = true;
        return new RxJava2CallAdapter((Type)object, this.scheduler, bl4, bl5, bl, bl2, bl3, false);
    }
}

