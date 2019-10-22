/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.mixpanel.android.util;

import android.content.Context;
import com.mixpanel.android.util.OfflineMode;
import java.io.IOException;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;

public interface RemoteService {
    public void checkIsMixpanelBlocked();

    public boolean isOnline(Context var1, OfflineMode var2);

    public byte[] performRequest(String var1, Map<String, Object> var2, SSLSocketFactory var3) throws ServiceUnavailableException, IOException;

    public static class ServiceUnavailableException
    extends Exception {
        private final int mRetryAfter;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ServiceUnavailableException(String string2, String string3) {
            super(string2);
            int n;
            try {
                n = Integer.parseInt(string3);
            }
            catch (NumberFormatException numberFormatException) {
                n = 0;
            }
            this.mRetryAfter = n;
        }

        public int getRetryAfter() {
            return this.mRetryAfter;
        }
    }

}

