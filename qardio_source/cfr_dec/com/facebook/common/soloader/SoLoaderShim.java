/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.soloader;

public class SoLoaderShim {
    private static volatile Handler sHandler = new DefaultHandler();

    public static void loadLibrary(String string2) {
        sHandler.loadLibrary(string2);
    }

    public static class DefaultHandler
    implements Handler {
        @Override
        public void loadLibrary(String string2) {
            System.loadLibrary(string2);
        }
    }

    public static interface Handler {
        public void loadLibrary(String var1);
    }

}

