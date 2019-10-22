/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.file;

import com.facebook.common.file.FileTreeVisitor;
import java.io.File;

public class FileTree {
    public static boolean deleteContents(File arrfile) {
        arrfile = arrfile.listFiles();
        boolean bl = true;
        boolean bl2 = true;
        if (arrfile != null) {
            int n = arrfile.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                bl2 &= FileTree.deleteRecursively(arrfile[n2]);
                ++n2;
            } while (true);
        }
        return bl;
    }

    public static boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            FileTree.deleteContents(file);
        }
        return file.delete();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void walkFileTree(File file, FileTreeVisitor fileTreeVisitor) {
        fileTreeVisitor.preVisitDirectory(file);
        File[] arrfile = file.listFiles();
        if (arrfile != null) {
            for (File file2 : arrfile) {
                if (file2.isDirectory()) {
                    FileTree.walkFileTree(file2, fileTreeVisitor);
                    continue;
                }
                fileTreeVisitor.visitFile(file2);
            }
        }
        fileTreeVisitor.postVisitDirectory(file);
    }
}

