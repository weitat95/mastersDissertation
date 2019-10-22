/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.producers.ProducerContextCallbacks;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUrlConnectionNetworkFetcher
extends BaseNetworkFetcher<FetchState> {
    private final ExecutorService mExecutorService;

    public HttpUrlConnectionNetworkFetcher() {
        this(Executors.newFixedThreadPool(3));
    }

    HttpUrlConnectionNetworkFetcher(ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private HttpURLConnection downloadFrom(Uri object, int n) throws IOException {
        void var1_3;
        void var2_5;
        HttpURLConnection httpURLConnection = HttpUrlConnectionNetworkFetcher.openConnectionTo(object);
        int n2 = httpURLConnection.getResponseCode();
        if (HttpUrlConnectionNetworkFetcher.isHttpSuccess(n2)) {
            return httpURLConnection;
        }
        if (!HttpUrlConnectionNetworkFetcher.isHttpRedirect(n2)) {
            httpURLConnection.disconnect();
            throw new IOException(String.format("Image URL %s returned HTTP code %d", object.toString(), n2));
        }
        String string2 = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();
        httpURLConnection = string2 == null ? null : Uri.parse((String)string2);
        string2 = object.getScheme();
        if (var2_5 > 0 && httpURLConnection != null && !httpURLConnection.getScheme().equals(string2)) {
            return this.downloadFrom((Uri)httpURLConnection, (int)(var2_5 - true));
        }
        if (var2_5 == false) {
            String string3 = HttpUrlConnectionNetworkFetcher.error("URL %s follows too many redirects", object.toString());
            throw new IOException((String)var1_3);
        }
        String string4 = HttpUrlConnectionNetworkFetcher.error("URL %s returned %d without a valid redirect", object.toString(), n2);
        throw new IOException((String)var1_3);
    }

    private static String error(String string2, Object ... arrobject) {
        return String.format(Locale.getDefault(), string2, arrobject);
    }

    private static boolean isHttpRedirect(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 300: 
            case 301: 
            case 302: 
            case 303: 
            case 307: 
            case 308: 
        }
        return true;
    }

    private static boolean isHttpSuccess(int n) {
        return n >= 200 && n < 300;
    }

    static HttpURLConnection openConnectionTo(Uri uri) throws IOException {
        return (HttpURLConnection)new URL(uri.toString()).openConnection();
    }

    @Override
    public FetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        return new FetchState(consumer, producerContext);
    }

    @Override
    public void fetch(final FetchState fetchState, final NetworkFetcher.Callback callback) {
        final Future<?> future = this.mExecutorService.submit(new Runnable(){

            @Override
            public void run() {
                HttpUrlConnectionNetworkFetcher.this.fetchSync(fetchState, callback);
            }
        });
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks(){

            @Override
            public void onCancellationRequested() {
                if (future.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void fetchSync(FetchState object, NetworkFetcher.Callback callback) {
        Object object2 = null;
        Object object3 = null;
        try {
            object = this.downloadFrom(((FetchState)object).getUri(), 5);
            if (object != null) {
                object3 = object;
                object2 = object;
                callback.onResponse(((URLConnection)object).getInputStream(), -1);
            }
            if (object == null) return;
            {
                ((HttpURLConnection)object).disconnect();
                return;
            }
        }
        catch (IOException iOException) {
            object2 = object3;
            try {
                callback.onFailure(iOException);
                if (object3 == null) return;
                {
                    ((HttpURLConnection)object3).disconnect();
                    return;
                }
            }
            catch (Throwable throwable) {
                if (object2 == null) throw throwable;
                {
                    ((HttpURLConnection)object2).disconnect();
                }
                throw throwable;
            }
        }
    }

}

