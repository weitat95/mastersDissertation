/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzfy;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

final class zzfw
implements zzfy {
    zzfw() {
    }

    @Override
    public final HttpURLConnection zzc(URL uRL) throws IOException {
        return (HttpURLConnection)uRL.openConnection();
    }
}

