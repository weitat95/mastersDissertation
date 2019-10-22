/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.util.RxEventBus;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class UnauthorisedInterceptor
implements Interceptor {
    private final RxEventBus rxEventBus;

    public UnauthorisedInterceptor(RxEventBus rxEventBus) {
        this.rxEventBus = rxEventBus;
    }

    @Override
    public Response intercept(Interceptor.Chain object) throws IOException {
        object = object.proceed(object.request());
        BufferedSource bufferedSource = ((Response)object).body().source();
        bufferedSource.request(Long.MAX_VALUE);
        if (((Buffer)bufferedSource.buffer().clone()).readUtf8().contains("hsynch.invalid.ticket")) {
            this.rxEventBus.post(new RxEventBus.UnauthorizedResponse());
        }
        return object;
    }
}

