/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 */
package com.getqardio.android.utils.ui;

import android.util.Pair;

public class BasicNameValuePair
extends Pair<String, String> {
    public BasicNameValuePair(String string2, String string3) {
        super((Object)string2, (Object)string3);
    }

    public String getName() {
        return (String)this.first;
    }

    public String getValue() {
        return (String)this.second;
    }

    public String toString() {
        return (String)this.first + "=" + (String)this.second;
    }
}

