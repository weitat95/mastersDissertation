/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import timber.log.Timber;

public class FnFNotificationStack {
    private static FnFNotificationStack instance = new FnFNotificationStack();
    private ArrayList<String> stack = new ArrayList();

    public static FnFNotificationStack getInstance() {
        return instance;
    }

    public void clearCache() {
        Timber.d("clearCache called", new Object[0]);
        this.stack.clear();
    }

    public List<String> getLatestMessages() {
        Timber.d("getLatestMessages " + this.stack.size(), new Object[0]);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(this.stack);
        Collections.reverse(arrayList);
        return arrayList;
    }

    public void saveNotificationMessage(String string2) {
        this.stack.add(string2);
    }
}

