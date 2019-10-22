/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.net.http.X509TrustManagerExtensions
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Base64
 *  android.util.Pair
 */
package com.getqardio.android.io.network;

import android.content.Context;
import android.net.Uri;
import android.net.http.X509TrustManagerExtensions;
import android.os.Build;
import android.util.Base64;
import android.util.Pair;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import timber.log.Timber;

public abstract class NetworkRequestHelper {
    private static String userAgent = NetworkRequestHelper.generateUserAgentString();

    private static void addHeaders(HttpURLConnection httpURLConnection, Collection<Pair<String, String>> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Pair pair = (Pair)object.next();
                httpURLConnection.setRequestProperty((String)pair.first, (String)pair.second);
            }
        }
    }

    private static String generateUserAgentString() {
        String string2 = Build.MANUFACTURER;
        String string3 = Build.MODEL;
        String string4 = Build.PRODUCT;
        return String.format("QardioAndroid/%s (Android %s; Runtime/%s; %s %s) - %s", "1.30", Build.VERSION.RELEASE, System.getProperty("java.vm.version"), string2, string3, "release");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static HttpResponse get(String string2, Collection<Pair<String, String>> object) {
        Object object2;
        X509TrustManagerExtensions x509TrustManagerExtensions;
        int n;
        int n2;
        Object object3;
        try {
            object2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            ((TrustManagerFactory)object2).init((KeyStore)null);
            x509TrustManagerExtensions = null;
            object3 = ((TrustManagerFactory)object2).getTrustManagers();
            n2 = ((TrustManager[])object3).length;
            n = 0;
        }
        catch (Exception exception) {
            Timber.e(exception, "In get method for url [ %s ]", string2);
            return new HttpResponse(406, "");
        }
        do {
            block8: {
                block7: {
                    object2 = x509TrustManagerExtensions;
                    if (n >= n2) break block7;
                    object2 = object3[n];
                    if (!(object2 instanceof X509TrustManager)) break block8;
                    object2 = (X509TrustManager)object2;
                }
                x509TrustManagerExtensions = new X509TrustManagerExtensions((X509TrustManager)object2);
                object3 = new URL(string2);
                object2 = (HttpsURLConnection)((URL)object3).openConnection();
                String string3 = NetworkContract.OAuthData.generateAuthorization();
                n = NetworkRequestHelper.getTimeout();
                ((URLConnection)object2).setReadTimeout(n);
                ((URLConnection)object2).setConnectTimeout(n);
                ((HttpURLConnection)object2).setRequestMethod("GET");
                ((URLConnection)object2).setUseCaches(false);
                ((URLConnection)object2).setDoInput(true);
                ((URLConnection)object2).setDoOutput(false);
                if (string3 != null) {
                    ((URLConnection)object2).setRequestProperty("Authorization", string3);
                }
                ((URLConnection)object2).setRequestProperty("User-Agent", userAgent);
                ((URLConnection)object2).setRequestProperty("Connection", "Keep-Alive");
                ((URLConnection)object2).setRequestProperty("Content-Type", "application/json");
                ((URLConnection)object2).setRequestProperty("Accept", "application/json");
                NetworkRequestHelper.addHeaders((HttpURLConnection)object2, (Collection<Pair<String, String>>)object);
                ((URLConnection)object2).connect();
                object = Collections.singleton("5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=");
                NetworkRequestHelper.validatePinning(((URL)object3).getAuthority(), x509TrustManagerExtensions, (HttpsURLConnection)object2, object);
                object = ((HttpURLConnection)object2).getResponseCode() == 200 ? ((URLConnection)object2).getInputStream() : ((HttpURLConnection)object2).getErrorStream();
                object = NetworkRequestHelper.readStreamAsString((InputStream)object);
                return new HttpResponse(((HttpURLConnection)object2).getResponseCode(), (String)object);
            }
            ++n;
        } while (true);
    }

    private static int getTimeout() {
        if (Utils.isNetworkWiFi((Context)CustomApplication.getApplication())) {
            return 20000;
        }
        return 25000;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static HttpResponse post(String string2, String object, Collection<Pair<String, String>> object2) {
        int n;
        Object object3;
        int n2;
        X509TrustManagerExtensions x509TrustManagerExtensions;
        Object object4;
        try {
            object4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            ((TrustManagerFactory)object4).init((KeyStore)null);
            x509TrustManagerExtensions = null;
            object3 = ((TrustManagerFactory)object4).getTrustManagers();
            n = ((TrustManager[])object3).length;
            n2 = 0;
        }
        catch (Exception exception) {
            Timber.e(exception, "In post method for url [ %s ]", string2);
            return new HttpResponse(406, "");
        }
        do {
            block8: {
                block7: {
                    object4 = x509TrustManagerExtensions;
                    if (n2 >= n) break block7;
                    object4 = object3[n2];
                    if (!(object4 instanceof X509TrustManager)) break block8;
                    object4 = (X509TrustManager)object4;
                }
                x509TrustManagerExtensions = new X509TrustManagerExtensions((X509TrustManager)object4);
                object3 = new URL(string2);
                object4 = (HttpsURLConnection)((URL)object3).openConnection();
                String string3 = NetworkContract.OAuthData.generateAuthorization();
                n2 = NetworkRequestHelper.getTimeout();
                ((URLConnection)object4).setReadTimeout(n2);
                ((URLConnection)object4).setConnectTimeout(n2);
                ((HttpURLConnection)object4).setRequestMethod("POST");
                ((URLConnection)object4).setUseCaches(false);
                ((URLConnection)object4).setDoInput(true);
                ((URLConnection)object4).setDoOutput(true);
                if (string3 != null) {
                    ((URLConnection)object4).setRequestProperty("Authorization", string3);
                }
                ((URLConnection)object4).setRequestProperty("User-Agent", userAgent);
                ((URLConnection)object4).setRequestProperty("Connection", "Keep-Alive");
                ((URLConnection)object4).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                ((URLConnection)object4).setRequestProperty("Accept", "application/json");
                NetworkRequestHelper.addHeaders((HttpURLConnection)object4, (Collection<Pair<String, String>>)object2);
                object2 = new BufferedOutputStream(((URLConnection)object4).getOutputStream());
                ((OutputStream)object2).write(((String)object).getBytes());
                ((OutputStream)object2).close();
                ((URLConnection)object4).connect();
                object = Collections.singleton("5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=");
                NetworkRequestHelper.validatePinning(((URL)object3).getAuthority(), x509TrustManagerExtensions, (HttpsURLConnection)object4, object);
                object = ((HttpURLConnection)object4).getResponseCode() == 200 ? ((URLConnection)object4).getInputStream() : ((HttpURLConnection)object4).getErrorStream();
                object = NetworkRequestHelper.readStreamAsString((InputStream)object);
                return new HttpResponse(((HttpURLConnection)object4).getResponseCode(), (String)object);
            }
            ++n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String readStreamAsString(InputStream var0) {
        block15: {
            var1_4 = null;
            var3_6 = null;
            var4_7 = new StringBuilder();
            var0 = new BufferedReader(new InputStreamReader((InputStream)var0));
            try {
                while ((var1_4 = var0.readLine()) != null) {
                    var4_7.append((String)var1_4);
                }
                break block15;
            }
            catch (IOException var2_8) {}
            ** GOTO lbl-1000
        }
        if (var0 == null) return var4_7.toString();
        try {
            var0.close();
            return var4_7.toString();
        }
        catch (IOException var0_1) {
            var0_1.printStackTrace();
            return var4_7.toString();
        }
        catch (Throwable var2_10) {
            var1_4 = var0;
            var0 = var2_10;
            ** GOTO lbl-1000
        }
lbl-1000:
        // 2 sources
        {
            do {
                var1_4 = var0;
                try {
                    var2_9.printStackTrace();
                    if (var0 == null) return var4_7.toString();
                }
                catch (Throwable var0_3) lbl-1000:
                // 2 sources
                {
                    if (var1_4 == null) throw var0;
                    try {
                        var1_4.close();
                    }
                    catch (IOException var1_5) {
                        var1_5.printStackTrace();
                        throw var0;
                    }
                    throw var0;
                }
                try {
                    var0.close();
                    return var4_7.toString();
                }
                catch (IOException var0_2) {
                    var0_2.printStackTrace();
                    return var4_7.toString();
                }
                break;
            } while (true);
        }
        catch (IOException var2_11) {
            var0 = var3_6;
            ** continue;
        }
    }

    public static HttpResponse request(Method method, Uri uri, List<BasicNameValuePair> list) {
        return NetworkRequestHelper.request(method, uri, list, null);
    }

    public static HttpResponse request(Method object, Uri object2, List<BasicNameValuePair> object3, Collection<Pair<String, String>> collection) {
        Timber.d("-> serverRequest: Method=%s, Uri=%s, params=%s", ((Enum)object).toString(), object2.toString(), object3.toString());
        switch (1.$SwitchMap$com$getqardio$android$io$network$NetworkRequestHelper$Method[((Enum)object).ordinal()]) {
            default: {
                throw new NullPointerException("Method can't be null");
            }
            case 1: {
                object = object2.buildUpon();
                object2 = object3.iterator();
                while (object2.hasNext()) {
                    object3 = (BasicNameValuePair)((Object)object2.next());
                    object.appendQueryParameter(((BasicNameValuePair)((Object)object3)).getName(), ((BasicNameValuePair)((Object)object3)).getValue());
                }
                object = NetworkRequestHelper.get(object.build().toString(), collection);
                Timber.d("<- serverResponse: %s", ((HttpResponse)object).toString());
                return object;
            }
            case 2: 
        }
        object = new StringBuilder();
        object3 = object3.iterator();
        while (object3.hasNext()) {
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair)((Object)object3.next());
            if (((StringBuilder)object).length() == 0) {
                ((StringBuilder)object).append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue());
                continue;
            }
            ((StringBuilder)object).append("&").append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue());
        }
        object = NetworkRequestHelper.post(object2.toString(), ((StringBuilder)object).toString(), collection);
        Timber.d("<- serverResponse: %s", ((HttpResponse)object).toString());
        return object;
    }

    private static List<X509Certificate> trustedChain(X509TrustManagerExtensions object, HttpsURLConnection object2) throws SSLException {
        Certificate[] arrcertificate = ((HttpsURLConnection)object2).getServerCertificates();
        arrcertificate = (X509Certificate[])Arrays.copyOf(arrcertificate, arrcertificate.length, X509Certificate[].class);
        object2 = ((URLConnection)object2).getURL().getHost();
        try {
            object = object.checkServerTrusted((X509Certificate[])arrcertificate, "RSA", (String)object2);
            return object;
        }
        catch (CertificateException certificateException) {
            throw new SSLException(certificateException);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void validatePinning(String string2, X509TrustManagerExtensions object, HttpsURLConnection object22, Set<String> set) throws SSLException {
        if (!"oauth2.getqardio.com".equals(string2) && !"api.getqardio.com".equals(string2)) {
            return;
        }
        string2 = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            for (X509Certificate x509Certificate : NetworkRequestHelper.trustedChain((X509TrustManagerExtensions)object, (HttpsURLConnection)object22)) {
                void var3_6;
                Object object2 = x509Certificate.getPublicKey().getEncoded();
                messageDigest.update((byte[])object2, 0, ((byte[])object2).length);
                object2 = Base64.encodeToString((byte[])messageDigest.digest(), (int)2);
                string2 = string2 + "    sha256/" + (String)object2 + " : " + x509Certificate.getSubjectDN().toString() + "\n";
                if (!var3_6.contains(object2)) continue;
                Timber.d("cert pinned!", new Object[0]);
                return;
            }
        }
        catch (Exception exception) {
            Timber.d("cert not pinned!", new Object[0]);
            throw new SSLException(exception);
        }
        throw new SSLPeerUnverifiedException("Certificate pinning failure\n  Peer certificate chain:\n" + string2);
    }

    public static class HttpResponse {
        private String responseBody;
        private int responseCode;

        public HttpResponse(int n, String string2) {
            this.responseCode = n;
            this.responseBody = string2;
        }

        public String getResponseBody() {
            return this.responseBody;
        }

        public int getResponseCode() {
            return this.responseCode;
        }

        public boolean isSuccessful() {
            return this.responseCode == 200;
        }

        public String toString() {
            return this.responseCode + "[" + this.responseBody + "]";
        }
    }

    public static enum Method {
        GET,
        POST;

    }

}

