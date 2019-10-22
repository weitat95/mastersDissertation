/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.ExceptionParser;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class StandardExceptionParser
implements ExceptionParser {
    private final TreeSet<String> zzdqf = new TreeSet();

    public StandardExceptionParser(Context context, Collection<String> collection) {
        this.setIncludedPackages(context, collection);
    }

    protected StackTraceElement getBestStackTraceElement(Throwable arrstackTraceElement) {
        if ((arrstackTraceElement = arrstackTraceElement.getStackTrace()) == null || arrstackTraceElement.length == 0) {
            return null;
        }
        int n = arrstackTraceElement.length;
        for (int i = 0; i < n; ++i) {
            StackTraceElement stackTraceElement = arrstackTraceElement[i];
            String string2 = stackTraceElement.getClassName();
            Iterator<String> iterator = this.zzdqf.iterator();
            while (iterator.hasNext()) {
                if (!string2.startsWith(iterator.next())) continue;
                return stackTraceElement;
            }
        }
        return arrstackTraceElement[0];
    }

    protected Throwable getCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    @Override
    public String getDescription(String string2, Throwable throwable) {
        return this.getDescription(this.getCause(throwable), this.getBestStackTraceElement(this.getCause(throwable)), string2);
    }

    protected String getDescription(Throwable object, StackTraceElement stackTraceElement, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object.getClass().getSimpleName());
        if (stackTraceElement != null) {
            String[] arrstring = stackTraceElement.getClassName().split("\\.");
            String string3 = "unknown";
            object = string3;
            if (arrstring != null) {
                object = string3;
                if (arrstring.length > 0) {
                    object = arrstring[arrstring.length - 1];
                }
            }
            stringBuilder.append(String.format(" (@%s:%s:%s)", object, stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
        }
        if (string2 != null) {
            stringBuilder.append(String.format(" {%s}", string2));
        }
        return stringBuilder.toString();
    }

    public void setIncludedPackages(Context object, Collection<String> object2) {
        this.zzdqf.clear();
        Object object3 = new HashSet<String>();
        if (object2 != null) {
            object3.addAll(object2);
        }
        if (object != null) {
            object3.add(object.getApplicationContext().getPackageName());
        }
        object = object3.iterator();
        block0 : while (object.hasNext()) {
            object2 = (String)object.next();
            object3 = this.zzdqf.iterator();
            boolean bl = true;
            do {
                block9: {
                    block8: {
                        if (!object3.hasNext()) break block8;
                        String string2 = (String)object3.next();
                        if (((String)object2).startsWith(string2)) break block9;
                        if (string2.startsWith((String)object2)) {
                            this.zzdqf.remove(string2);
                        }
                    }
                    if (!bl) continue block0;
                    this.zzdqf.add((String)object2);
                    continue block0;
                }
                bl = false;
            } while (true);
        }
    }
}

