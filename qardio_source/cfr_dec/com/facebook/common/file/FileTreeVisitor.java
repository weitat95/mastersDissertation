/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.file;

import java.io.File;

public interface FileTreeVisitor {
    public void postVisitDirectory(File var1);

    public void preVisitDirectory(File var1);

    public void visitFile(File var1);
}

