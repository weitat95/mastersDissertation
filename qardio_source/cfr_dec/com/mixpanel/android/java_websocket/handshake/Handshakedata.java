/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import java.util.Iterator;

public interface Handshakedata {
    public byte[] getContent();

    public String getFieldValue(String var1);

    public boolean hasFieldValue(String var1);

    public Iterator<String> iterateHttpFields();
}

