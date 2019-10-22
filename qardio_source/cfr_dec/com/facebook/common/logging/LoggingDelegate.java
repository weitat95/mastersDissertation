/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.logging;

public interface LoggingDelegate {
    public void d(String var1, String var2);

    public void e(String var1, String var2);

    public void e(String var1, String var2, Throwable var3);

    public void i(String var1, String var2);

    public boolean isLoggable(int var1);

    public void v(String var1, String var2);

    public void w(String var1, String var2);

    public void w(String var1, String var2, Throwable var3);

    public void wtf(String var1, String var2);

    public void wtf(String var1, String var2, Throwable var3);
}

