/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.network.NetworkUtils;
import io.fabric.sdk.android.services.network.PinningInfoProvider;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DefaultHttpRequestFactory
implements HttpRequestFactory {
    private boolean attemptedSslInit;
    private final Logger logger;
    private PinningInfoProvider pinningInfo;
    private SSLSocketFactory sslSocketFactory;

    public DefaultHttpRequestFactory() {
        this(new DefaultLogger());
    }

    public DefaultHttpRequestFactory(Logger logger) {
        this.logger = logger;
    }

    private SSLSocketFactory getSSLSocketFactory() {
        synchronized (this) {
            if (this.sslSocketFactory == null && !this.attemptedSslInit) {
                this.sslSocketFactory = this.initSSLSocketFactory();
            }
            SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
            return sSLSocketFactory;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private SSLSocketFactory initSSLSocketFactory() {
        synchronized (this) {
            this.attemptedSslInit = true;
            try {
                SSLSocketFactory sSLSocketFactory = NetworkUtils.getSSLSocketFactory(this.pinningInfo);
                this.logger.d("Fabric", "Custom SSL pinning enabled");
                return sSLSocketFactory;
            }
            catch (Exception exception) {
                this.logger.e("Fabric", "Exception while validating pinned certs", exception);
                return null;
            }
        }
    }

    private boolean isHttps(String string2) {
        return string2 != null && string2.toLowerCase(Locale.US).startsWith("https");
    }

    private void resetSSLSocketFactory() {
        synchronized (this) {
            this.attemptedSslInit = false;
            this.sslSocketFactory = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public HttpRequest buildHttpRequest(HttpMethod object, String object2, Map<String, String> map) {
        switch (1.$SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[((Enum)object).ordinal()]) {
            default: {
                throw new IllegalArgumentException("Unsupported HTTP method!");
            }
            case 1: {
                object = HttpRequest.get((CharSequence)object2, map, true);
                break;
            }
            case 2: {
                object = HttpRequest.post((CharSequence)object2, map, true);
                break;
            }
            case 3: {
                object = HttpRequest.put((CharSequence)object2);
                break;
            }
            case 4: {
                object = HttpRequest.delete((CharSequence)object2);
            }
        }
        if (this.isHttps((String)object2) && this.pinningInfo != null && (object2 = this.getSSLSocketFactory()) != null) {
            ((HttpsURLConnection)((HttpRequest)object).getConnection()).setSSLSocketFactory((SSLSocketFactory)object2);
        }
        return object;
    }

    @Override
    public void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        if (this.pinningInfo != pinningInfoProvider) {
            this.pinningInfo = pinningInfoProvider;
            this.resetSSLSocketFactory();
        }
    }

}

