/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.segment.analytics;

import android.util.Base64;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionFactory {
    private String authorizationHeader(String string2) {
        return "Basic " + Base64.encodeToString((byte[])(string2 + ":").getBytes(), (int)2);
    }

    public HttpURLConnection attribution(String string2) throws IOException {
        HttpURLConnection httpURLConnection = this.openConnection("https://mobile-service.segment.com/v1/attribution");
        httpURLConnection.setRequestProperty("Authorization", this.authorizationHeader(string2));
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    protected HttpURLConnection openConnection(String object) throws IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        ((URLConnection)object).setConnectTimeout(15000);
        ((URLConnection)object).setReadTimeout(20000);
        ((URLConnection)object).setRequestProperty("Content-Type", "application/json");
        ((URLConnection)object).setDoInput(true);
        return object;
    }

    public HttpURLConnection projectSettings(String string2) throws IOException {
        return this.openConnection("https://cdn-settings.segment.com/v1/projects/" + string2 + "/settings");
    }

    public HttpURLConnection upload(String string2) throws IOException {
        HttpURLConnection httpURLConnection = this.openConnection("https://api.segment.io/v1/import");
        httpURLConnection.setRequestProperty("Authorization", this.authorizationHeader(string2));
        httpURLConnection.setRequestProperty("Content-Encoding", "gzip");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setChunkedStreamingMode(0);
        return httpURLConnection;
    }
}

