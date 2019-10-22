/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.shopify.buy3;

import android.os.Handler;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphCallCanceledError;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphNetworkError;
import com.shopify.buy3.GraphParseError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.HttpCallbackWithRetry;
import com.shopify.buy3.HttpResponseParser;
import com.shopify.buy3.RealGraphCall$CallbackProxy$$Lambda$1;
import com.shopify.buy3.cache.HttpCache;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Query;
import com.shopify.graphql.support.SchemaViolationError;
import com.shopify.graphql.support.TopLevelResponse;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

abstract class RealGraphCall<T extends AbstractResponse<T>>
implements GraphCall<T>,
Cloneable {
    static final MediaType GRAPHQL_MEDIA_TYPE = MediaType.parse("application/graphql; charset=utf-8");
    private volatile boolean canceled;
    protected final ScheduledExecutorService dispatcher;
    protected final AtomicBoolean executed = new AtomicBoolean();
    protected final HttpCache httpCache;
    protected final HttpCachePolicy.Policy httpCachePolicy;
    private volatile Call httpCall;
    protected final Call.Factory httpCallFactory;
    private volatile HttpCallbackWithRetry httpCallbackWithRetry;
    protected final HttpResponseParser<T> httpResponseParser;
    protected final Query query;
    private CallbackProxy<T> responseCallback;
    protected final HttpUrl serverUrl;

    RealGraphCall(RealGraphCall<T> realGraphCall) {
        this.query = realGraphCall.query;
        this.serverUrl = realGraphCall.serverUrl;
        this.httpCallFactory = realGraphCall.httpCallFactory;
        this.httpResponseParser = realGraphCall.httpResponseParser;
        this.dispatcher = realGraphCall.dispatcher;
        this.httpCachePolicy = realGraphCall.httpCachePolicy;
        this.httpCache = realGraphCall.httpCache;
    }

    RealGraphCall(Query query, HttpUrl httpUrl, Call.Factory factory, ResponseDataConverter<T> responseDataConverter, ScheduledExecutorService scheduledExecutorService, HttpCachePolicy.Policy policy, HttpCache httpCache) {
        this.query = query;
        this.serverUrl = httpUrl;
        this.httpCallFactory = factory;
        this.httpResponseParser = new HttpResponseParser<T>(responseDataConverter);
        this.dispatcher = scheduledExecutorService;
        this.httpCachePolicy = policy;
        this.httpCache = httpCache;
    }

    private void checkIfCanceled() throws GraphCallCanceledError {
        if (this.canceled) {
            throw new GraphCallCanceledError();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Call httpCall() {
        RequestBody requestBody = RequestBody.create(GRAPHQL_MEDIA_TYPE, this.query.toString());
        Object object = this.httpCache != null ? HttpCache.cacheKey(requestBody) : "";
        object = new Request.Builder().url(this.serverUrl).post(requestBody).header("Accept", "application/json").header("X-BUY3-SDK-CACHE-KEY", (String)object).header("X-BUY3-SDK-CACHE-FETCH-STRATEGY", this.httpCachePolicy.fetchStrategy.name()).header("X-BUY3-SDK-EXPIRE-TIMEOUT", String.valueOf(this.httpCachePolicy.expireTimeoutMs())).build();
        return this.httpCallFactory.newCall((Request)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void removeCachedResponse() {
        String string2 = this.httpCall != null ? this.httpCall.request().header("X-BUY3-SDK-CACHE-KEY") : null;
        if (this.httpCache != null && string2 != null && !string2.isEmpty()) {
            this.httpCache.removeQuietly(string2);
            return;
        }
    }

    @Override
    public void cancel() {
        this.canceled = true;
        CallbackProxy<T> callbackProxy = this.responseCallback;
        if (callbackProxy != null) {
            callbackProxy.cancel();
        }
        if ((callbackProxy = this.httpCallbackWithRetry) != null) {
            ((HttpCallbackWithRetry)((Object)callbackProxy)).cancel();
        }
        if ((callbackProxy = this.httpCall) != null) {
            callbackProxy.cancel();
        }
    }

    @Override
    public abstract GraphCall<T> clone();

    @Override
    public GraphResponse<T> execute() throws GraphError {
        Response response;
        if (!this.executed.compareAndSet(false, true)) {
            throw new IllegalStateException("Already Executed");
        }
        this.checkIfCanceled();
        try {
            this.httpCall = this.httpCall();
            response = this.httpCall.execute();
        }
        catch (IOException iOException) {
            this.checkIfCanceled();
            throw new GraphNetworkError("Failed to execute GraphQL http request", iOException);
        }
        try {
            GraphResponse<T> graphResponse = this.httpResponseParser.parse(response);
            if (graphResponse.hasErrors()) {
                this.removeCachedResponse();
            }
            this.checkIfCanceled();
            return graphResponse;
        }
        catch (Exception exception) {
            if (exception instanceof GraphParseError) {
                this.removeCachedResponse();
            }
            throw exception;
        }
        finally {
            response.close();
        }
    }

    private static class CallbackProxy<T extends AbstractResponse<T>>
    implements GraphCall.Callback<T> {
        final RealGraphCall<T> graphCall;
        final Handler handler;
        final AtomicReference<GraphCall.Callback<T>> originalCallbackRef;

        static /* synthetic */ void lambda$cancel$0(GraphCall.Callback callback) {
            callback.onFailure(new GraphCallCanceledError());
        }

        void cancel() {
            GraphCall.Callback callback;
            block3: {
                block2: {
                    callback = this.originalCallbackRef.getAndSet(null);
                    if (callback == null) break block2;
                    if (this.handler == null) break block3;
                    this.handler.post(RealGraphCall$CallbackProxy$$Lambda$1.lambdaFactory$(callback));
                }
                return;
            }
            callback.onFailure(new GraphCallCanceledError());
        }

        @Override
        public void onFailure(GraphError graphError) {
            GraphCall.Callback<T> callback;
            if (graphError instanceof GraphParseError) {
                this.graphCall.removeCachedResponse();
            }
            if ((callback = this.originalCallbackRef.get()) != null && this.originalCallbackRef.compareAndSet(callback, null)) {
                callback.onFailure(graphError);
            }
        }

        @Override
        public void onResponse(GraphResponse<T> graphResponse) {
            GraphCall.Callback<T> callback;
            if (graphResponse.hasErrors()) {
                this.graphCall.removeCachedResponse();
            }
            if ((callback = this.originalCallbackRef.get()) == null || !this.originalCallbackRef.compareAndSet(callback, null)) {
                return;
            }
            callback.onResponse(graphResponse);
        }
    }

    static interface ResponseDataConverter<R extends AbstractResponse<R>> {
        public R convert(TopLevelResponse var1) throws SchemaViolationError;
    }

}

