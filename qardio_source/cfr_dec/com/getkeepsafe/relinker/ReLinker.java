/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getkeepsafe.relinker;

import android.content.Context;
import com.getkeepsafe.relinker.ReLinkerInstance;
import java.io.File;

public class ReLinker {
    public static void loadLibrary(Context context, String string2, String string3) {
        ReLinker.loadLibrary(context, string2, string3, null);
    }

    public static void loadLibrary(Context context, String string2, String string3, LoadListener loadListener) {
        new ReLinkerInstance().loadLibrary(context, string2, string3, loadListener);
    }

    public static interface LibraryInstaller {
        public void installLibrary(Context var1, String[] var2, String var3, File var4, ReLinkerInstance var5);
    }

    public static interface LibraryLoader {
        public void loadLibrary(String var1);

        public void loadPath(String var1);

        public String mapLibraryName(String var1);

        public String[] supportedAbis();

        public String unmapLibraryName(String var1);
    }

    public static interface LoadListener {
        public void failure(Throwable var1);

        public void success();
    }

    public static interface Logger {
        public void log(String var1);
    }

}

