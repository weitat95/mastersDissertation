/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Utils;

final class ExecutorCallAdapterFactory
extends CallAdapter.Factory {
    final Executor callbackExecutor;

    ExecutorCallAdapterFactory(Executor executor) {
        this.callbackExecutor = executor;
    }

    public CallAdapter<Call<?>> get(Type type, Annotation[] arrannotation, Retrofit retrofit) {
        if (ExecutorCallAdapterFactory.getRawType(type) != Call.class) {
            return null;
        }
        return new CallAdapter<Call<?>>(Utils.getCallResponseType(type)){
            final /* synthetic */ Type val$responseType;
            {
                this.val$responseType = type;
            }

            @Override
            public <R> Call<R> adapt(Call<R> call) {
                return new ExecutorCallbackCall<R>(ExecutorCallAdapterFactory.this.callbackExecutor, call);
            }

            @Override
            public Type responseType() {
                return this.val$responseType;
            }
        };
    }

    static final class ExecutorCallbackCall<T>
    implements Call<T> {
        final Executor callbackExecutor;
        final Call<T> delegate;

        ExecutorCallbackCall(Executor executor, Call<T> call) {
            this.callbackExecutor = executor;
            this.delegate = call;
        }

        @Override
        public void cancel() {
            this.delegate.cancel();
        }

        @Override
        public Call<T> clone() {
            return new ExecutorCallbackCall<T>(this.callbackExecutor, this.delegate.clone());
        }

        @Override
        public Response<T> execute() throws IOException {
            return this.delegate.execute();
        }

        @Override
        public boolean isCanceled() {
            return this.delegate.isCanceled();
        }
    }

}

