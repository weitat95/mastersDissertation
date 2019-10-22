/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.util.Util;
import java.util.Iterator;
import java.util.List;

public final class UserError
extends RuntimeException {
    public UserError(String string2) {
        super(string2);
    }

    public UserError(List<String> list) {
        super(UserError.formatMessage(Util.checkNotEmpty(list, "messages can't be empty")));
    }

    private static String formatMessage(List<String> object) {
        StringBuilder stringBuilder = new StringBuilder();
        object = object.iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }
}

