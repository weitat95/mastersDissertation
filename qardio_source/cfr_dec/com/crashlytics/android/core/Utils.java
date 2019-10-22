/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void capFileCount(File arrfile, FilenameFilter object, int n, Comparator<File> comparator) {
        if ((arrfile = arrfile.listFiles((FilenameFilter)object)) == null || arrfile.length <= n) return;
        Arrays.sort(arrfile, comparator);
        int n2 = arrfile.length;
        int n3 = arrfile.length;
        int n4 = 0;
        while (n4 < n3) {
            object = arrfile[n4];
            if (n2 <= n) {
                return;
            }
            ((File)object).delete();
            --n2;
            ++n4;
        }
    }
}

