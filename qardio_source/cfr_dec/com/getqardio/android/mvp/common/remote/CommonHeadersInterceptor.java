/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.mvp.common.remote;

import android.os.Build;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonHeadersInterceptor
implements Interceptor {
    private static String userAgent = CommonHeadersInterceptor.generateUserAgentString();

    private static String generateUserAgentString() {
        String string2 = Build.MANUFACTURER;
        String string3 = Build.MODEL;
        return String.format("QardioAndroid/%s (Android %s; Runtime/%s; %s %s) - %s", "1.30", Build.VERSION.RELEASE, System.getProperty("java.vm.version"), string2, string3, "release");
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder().addHeader("User-Agent", userAgent).addHeader("Connection", "Keep-Alive").addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").build());
    }
}

