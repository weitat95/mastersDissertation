/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.util.Map;

interface Report {
    public Map<String, String> getCustomHeaders();

    public File getFile();

    public String getFileName();

    public File[] getFiles();

    public String getIdentifier();

    public void remove();
}

