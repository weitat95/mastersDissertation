/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.shopify.buy3;

import android.content.Context;
import com.shopify.buy3.GraphClient$$Lambda$1;
import com.shopify.buy3.GraphClient$Builder$$Lambda$1;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.MutationGraphCall;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.RealMutationGraphCall;
import com.shopify.buy3.RealQueryGraphCall;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.Utils;
import com.shopify.buy3.cache.HttpCache;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

public final class GraphClient {
    final HttpCachePolicy.Policy defaultHttpCachePolicy;
    final ScheduledExecutorService dispatcher;
    final HttpCache httpCache;
    final Call.Factory httpCallFactory;
    final HttpUrl serverUrl;

    private GraphClient(HttpUrl object, Call.Factory factory, HttpCachePolicy.Policy policy, HttpCache httpCache) {
        this.serverUrl = Utils.checkNotNull(object, "serverUrl == null");
        this.httpCallFactory = Utils.checkNotNull(factory, "httpCallFactory == null");
        this.defaultHttpCachePolicy = Utils.checkNotNull(policy, "defaultCachePolicy == null");
        this.httpCache = httpCache;
        object = new ScheduledThreadPoolExecutor(2, GraphClient$$Lambda$1.lambdaFactory$());
        ((ThreadPoolExecutor)object).setKeepAliveTime(1L, TimeUnit.SECONDS);
        ((ThreadPoolExecutor)object).allowCoreThreadTimeOut(true);
        this.dispatcher = object;
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    static /* synthetic */ Thread lambda$new$0(Runnable runnable) {
        return new Thread(runnable, "GraphClient Dispatcher");
    }

    public MutationGraphCall mutateGraph(Storefront.MutationQuery mutationQuery) {
        return new RealMutationGraphCall(mutationQuery, this.serverUrl, this.httpCallFactory, this.dispatcher, HttpCachePolicy.NETWORK_ONLY, this.httpCache);
    }

    public QueryGraphCall queryGraph(Storefront.QueryRootQuery queryRootQuery) {
        return new RealQueryGraphCall(queryRootQuery, this.serverUrl, this.httpCallFactory, this.dispatcher, this.defaultHttpCachePolicy, this.httpCache);
    }

    public static final class Builder {
        private static final long DEFAULT_HTTP_CONNECTION_TIME_OUT_MS = TimeUnit.SECONDS.toMillis(10L);
        private static final long DEFAULT_HTTP_READ_WRITE_TIME_OUT_MS = TimeUnit.SECONDS.toMillis(20L);
        private String accessToken;
        private final String applicationName;
        private HttpCachePolicy.Policy defaultHttpCachePolicy = HttpCachePolicy.NETWORK_ONLY;
        private HttpUrl endpointUrl;
        private HttpCache httpCache;
        private File httpCacheFolder;
        private long httpCacheMaxSize;
        private OkHttpClient httpClient;
        private String shopDomain;

        private Builder(Context context) {
            this.applicationName = Utils.checkNotNull(context, "context == null").getPackageName();
        }

        static /* synthetic */ Response lambda$sdkHeaderInterceptor$0(String string2, String string3, Interceptor.Chain chain) throws IOException {
            Object object = chain.request();
            object = ((Request)object).newBuilder().method(((Request)object).method(), ((Request)object).body());
            ((Request.Builder)object).header("User-Agent", "Mobile Buy SDK Android/3.0.6/" + string2);
            ((Request.Builder)object).header("X-SDK-Version", "3.0.6");
            ((Request.Builder)object).header("X-SDK-Variant", "android");
            ((Request.Builder)object).header("X-Shopify-Storefront-Access-Token", string3);
            return chain.proceed(((Request.Builder)object).build());
        }

        private static Interceptor sdkHeaderInterceptor(String string2, String string3) {
            return GraphClient$Builder$$Lambda$1.lambdaFactory$(string2, string3);
        }

        public Builder accessToken(String string2) {
            this.accessToken = Utils.checkNotBlank(string2, "accessToken == null");
            return this;
        }

        public GraphClient build() {
            if (this.endpointUrl == null) {
                Utils.checkNotBlank(this.shopDomain, "shopDomain == null");
                this.endpointUrl = HttpUrl.parse("https://" + this.shopDomain + "/api/graphql");
            }
            Utils.checkNotBlank(this.accessToken, "apiKey == null");
            Object object = this.httpCache;
            Object object2 = object;
            if (object == null) {
                object2 = object;
                if (this.httpCacheFolder != null) {
                    object2 = (this.endpointUrl.toString() + "/" + this.accessToken).getBytes(Charset.forName("UTF-8"));
                    object2 = new HttpCache(new File(this.httpCacheFolder, ByteString.of(object2).md5().hex()), this.httpCacheMaxSize);
                }
            }
            OkHttpClient okHttpClient = this.httpClient;
            object = okHttpClient;
            if (okHttpClient == null) {
                object = this.defaultOkHttpClient();
            }
            okHttpClient = ((OkHttpClient)object).newBuilder().addInterceptor(Builder.sdkHeaderInterceptor(this.applicationName, this.accessToken)).build();
            object = okHttpClient;
            if (object2 != null) {
                object = okHttpClient.newBuilder().addInterceptor(object2.httpInterceptor()).build();
            }
            return new GraphClient(this.endpointUrl, (Call.Factory)object, this.defaultHttpCachePolicy, (HttpCache)object2);
        }

        public Builder defaultHttpCachePolicy(HttpCachePolicy.Policy policy) {
            this.defaultHttpCachePolicy = Utils.checkNotNull(policy, "cachePolicy == null");
            return this;
        }

        OkHttpClient defaultOkHttpClient() {
            return new OkHttpClient.Builder().connectTimeout(DEFAULT_HTTP_CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS).readTimeout(DEFAULT_HTTP_READ_WRITE_TIME_OUT_MS, TimeUnit.MILLISECONDS).writeTimeout(DEFAULT_HTTP_READ_WRITE_TIME_OUT_MS, TimeUnit.MILLISECONDS).build();
        }

        public Builder httpCache(File file, long l) {
            this.httpCacheFolder = Utils.checkNotNull(file, "folder == null");
            this.httpCacheMaxSize = l;
            return this;
        }

        public Builder httpClient(OkHttpClient okHttpClient) {
            this.httpClient = Utils.checkNotNull(okHttpClient, "httpClient == null");
            return this;
        }

        public Builder shopDomain(String string2) {
            this.shopDomain = Utils.checkNotBlank(string2, "shopDomain == null");
            return this;
        }
    }

}

