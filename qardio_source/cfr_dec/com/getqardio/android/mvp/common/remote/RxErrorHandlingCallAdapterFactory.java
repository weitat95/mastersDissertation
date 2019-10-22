/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.ApiResponseException;
import com.getqardio.android.mvp.common.remote.RetrofitException;
import com.getqardio.android.mvp.common.remote.RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1;
import com.getqardio.android.mvp.common.remote.StandardErrorResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class RxErrorHandlingCallAdapterFactory
extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original = RxJava2CallAdapterFactory.create();

    private RxErrorHandlingCallAdapterFactory() {
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type type, Annotation[] arrannotation, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, this.original.get(type, arrannotation, retrofit));
    }

    private static class RxCallAdapterWrapper
    implements CallAdapter<Single<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> callAdapter) {
            this.retrofit = retrofit;
            this.wrapped = callAdapter;
        }

        private Throwable asRetrofitException(Throwable object) {
            if (object instanceof ApiResponseException) {
                return object;
            }
            if (object instanceof HttpException) {
                object = ((HttpException)object).response();
                return RetrofitException.httpError(((retrofit2.Response)object).raw().request().url().toString(), (retrofit2.Response)object, this.retrofit);
            }
            if (object instanceof IOException) {
                return RetrofitException.networkError((IOException)object);
            }
            return RetrofitException.unexpectedError((Throwable)object);
        }

        static /* synthetic */ Object lambda$adapt$0(Object object) throws Exception {
            String string2;
            StandardErrorResponse standardErrorResponse;
            if (object instanceof StandardErrorResponse && (string2 = (standardErrorResponse = (StandardErrorResponse)object).getStatus()) != null && string2.equalsIgnoreCase("failure")) {
                return Single.error(new ApiResponseException(standardErrorResponse));
            }
            return Single.just(object);
        }

        @Override
        public <R> Single<?> adapt(Call<R> call) {
            return ((Single)this.wrapped.adapt(call)).flatMap(RxErrorHandlingCallAdapterFactory$RxCallAdapterWrapper$$Lambda$1.lambdaFactory$()).onErrorResumeNext(new Function<Throwable, SingleSource>(){

                @Override
                public SingleSource apply(Throwable throwable) throws Exception {
                    return Single.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                }
            });
        }

        @Override
        public Type responseType() {
            return this.wrapped.responseType();
        }

    }

}

