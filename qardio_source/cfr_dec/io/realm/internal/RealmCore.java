/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.realm.internal;

import android.content.Context;
import com.getkeepsafe.relinker.ReLinker;
import java.io.File;

public class RealmCore {
    private static final String BINARIES_PATH;
    private static final String FILE_SEP;
    private static final String PATH_SEP;
    private static volatile boolean libraryIsLoaded;

    static {
        FILE_SEP = File.separator;
        PATH_SEP = File.pathSeparator;
        BINARIES_PATH = "lib" + PATH_SEP + ".." + FILE_SEP + "lib";
        libraryIsLoaded = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void loadLibrary(Context context) {
        synchronized (RealmCore.class) {
            block6: {
                boolean bl = libraryIsLoaded;
                if (!bl) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            ReLinker.loadLibrary(context, "realm-jni", "3.5.0");
            libraryIsLoaded = true;
            return;
        }
    }
}

