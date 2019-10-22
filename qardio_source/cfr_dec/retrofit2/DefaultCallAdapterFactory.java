/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.Utils;

final class DefaultCallAdapterFactory
extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    DefaultCallAdapterFactory() {
    }

    @Override
    public CallAdapter<?> get(Type type, Annotation[] arrannotation, Retrofit retrofit) {
        if (DefaultCallAdapterFactory.getRawType(type) != Call.class) {
            return null;
        }
        return new CallAdapter<Call<?>>(Utils.getCallResponseType(type)){
            final /* synthetic */ Type val$responseType;
            {
                this.val$responseType = type;
            }

            @Override
            public <R> Call<R> adapt(Call<R> call) {
                return call;
            }

            @Override
            public Type responseType() {
                return this.val$responseType;
            }
        };
    }

}

