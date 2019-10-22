/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.facebook.common.logging;

import android.util.Log;
import com.facebook.common.logging.LoggingDelegate;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class FLogDefaultLoggingDelegate
implements LoggingDelegate {
    public static final FLogDefaultLoggingDelegate sInstance = new FLogDefaultLoggingDelegate();
    private String mApplicationTag = "unknown";
    private int mMinimumLoggingLevel = 5;

    private FLogDefaultLoggingDelegate() {
    }

    public static FLogDefaultLoggingDelegate getInstance() {
        return sInstance;
    }

    private static String getMsg(String string2, Throwable throwable) {
        return string2 + '\n' + FLogDefaultLoggingDelegate.getStackTraceString(throwable);
    }

    private static String getStackTraceString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private String prefixTag(String string2) {
        String string3 = string2;
        if (this.mApplicationTag != null) {
            string3 = this.mApplicationTag + ":" + string2;
        }
        return string3;
    }

    private void println(int n, String string2, String string3) {
        Log.println((int)n, (String)this.prefixTag(string2), (String)string3);
    }

    private void println(int n, String string2, String string3, Throwable throwable) {
        Log.println((int)n, (String)this.prefixTag(string2), (String)FLogDefaultLoggingDelegate.getMsg(string3, throwable));
    }

    @Override
    public void d(String string2, String string3) {
        this.println(3, string2, string3);
    }

    @Override
    public void e(String string2, String string3) {
        this.println(6, string2, string3);
    }

    @Override
    public void e(String string2, String string3, Throwable throwable) {
        this.println(6, string2, string3, throwable);
    }

    @Override
    public void i(String string2, String string3) {
        this.println(4, string2, string3);
    }

    @Override
    public boolean isLoggable(int n) {
        return this.mMinimumLoggingLevel <= n;
    }

    @Override
    public void v(String string2, String string3) {
        this.println(2, string2, string3);
    }

    @Override
    public void w(String string2, String string3) {
        this.println(5, string2, string3);
    }

    @Override
    public void w(String string2, String string3, Throwable throwable) {
        this.println(5, string2, string3, throwable);
    }

    @Override
    public void wtf(String string2, String string3) {
        this.println(6, string2, string3);
    }

    @Override
    public void wtf(String string2, String string3, Throwable throwable) {
        this.println(6, string2, string3, throwable);
    }
}

