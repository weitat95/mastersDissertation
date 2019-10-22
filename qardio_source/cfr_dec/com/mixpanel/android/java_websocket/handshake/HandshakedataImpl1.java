/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class HandshakedataImpl1
implements HandshakeBuilder {
    private byte[] content;
    private TreeMap<String, String> map = new TreeMap(String.CASE_INSENSITIVE_ORDER);

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public String getFieldValue(String string2) {
        String string3;
        string2 = string3 = this.map.get(string2);
        if (string3 == null) {
            string2 = "";
        }
        return string2;
    }

    @Override
    public boolean hasFieldValue(String string2) {
        return this.map.containsKey(string2);
    }

    @Override
    public Iterator<String> iterateHttpFields() {
        return Collections.unmodifiableSet(this.map.keySet()).iterator();
    }

    @Override
    public void put(String string2, String string3) {
        this.map.put(string2, string3);
    }

    @Override
    public void setContent(byte[] arrby) {
        this.content = arrby;
    }
}

