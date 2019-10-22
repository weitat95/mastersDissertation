/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.bumptech.glide.load.model;

import android.text.TextUtils;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class LazyHeaders
implements Headers {
    private volatile Map<String, String> combinedHeaders;
    private final Map<String, List<LazyHeaderFactory>> headers;

    LazyHeaders(Map<String, List<LazyHeaderFactory>> map) {
        this.headers = Collections.unmodifiableMap(map);
    }

    private Map<String, String> generateHeaders() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.headers.entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<LazyHeaderFactory> list = entry.getValue();
            for (int i = 0; i < list.size(); ++i) {
                stringBuilder.append(list.get(i).buildHeader());
                if (i == list.size() - 1) continue;
                stringBuilder.append(',');
            }
            hashMap.put(entry.getKey(), stringBuilder.toString());
        }
        return hashMap;
    }

    public boolean equals(Object object) {
        if (object instanceof LazyHeaders) {
            object = (LazyHeaders)object;
            return ((Object)this.headers).equals(((LazyHeaders)object).headers);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Map<String, String> getHeaders() {
        if (this.combinedHeaders == null) {
            synchronized (this) {
                if (this.combinedHeaders == null) {
                    this.combinedHeaders = Collections.unmodifiableMap(this.generateHeaders());
                }
            }
        }
        return this.combinedHeaders;
    }

    public int hashCode() {
        return ((Object)this.headers).hashCode();
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.headers + '}';
    }

    public static final class Builder {
        private static final Map<String, List<LazyHeaderFactory>> DEFAULT_HEADERS;
        private static final String DEFAULT_USER_AGENT;
        private boolean copyOnModify = true;
        private Map<String, List<LazyHeaderFactory>> headers = DEFAULT_HEADERS;
        private boolean isEncodingDefault = true;
        private boolean isUserAgentDefault = true;

        static {
            DEFAULT_USER_AGENT = System.getProperty("http.agent");
            HashMap<String, List<StringHeaderFactory>> hashMap = new HashMap<String, List<StringHeaderFactory>>(2);
            if (!TextUtils.isEmpty((CharSequence)DEFAULT_USER_AGENT)) {
                hashMap.put("User-Agent", Collections.singletonList(new StringHeaderFactory(DEFAULT_USER_AGENT)));
            }
            hashMap.put("Accept-Encoding", Collections.singletonList(new StringHeaderFactory("identity")));
            DEFAULT_HEADERS = Collections.unmodifiableMap(hashMap);
        }

        public LazyHeaders build() {
            this.copyOnModify = true;
            return new LazyHeaders(this.headers);
        }
    }

    static final class StringHeaderFactory
    implements LazyHeaderFactory {
        private final String value;

        StringHeaderFactory(String string2) {
            this.value = string2;
        }

        @Override
        public String buildHeader() {
            return this.value;
        }

        public boolean equals(Object object) {
            if (object instanceof StringHeaderFactory) {
                object = (StringHeaderFactory)object;
                return this.value.equals(((StringHeaderFactory)object).value);
            }
            return false;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.value + '\'' + '}';
        }
    }

}

