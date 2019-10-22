/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class InvalidSessionReport
implements Report {
    private final Map<String, String> customHeaders;
    private final File[] files;
    private final String identifier;

    public InvalidSessionReport(String string2, File[] arrfile) {
        this.files = arrfile;
        this.customHeaders = new HashMap<String, String>(ReportUploader.HEADER_INVALID_CLS_FILE);
        this.identifier = string2;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return Collections.unmodifiableMap(this.customHeaders);
    }

    @Override
    public File getFile() {
        return this.files[0];
    }

    @Override
    public String getFileName() {
        return this.files[0].getName();
    }

    @Override
    public File[] getFiles() {
        return this.files;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void remove() {
        for (File file : this.files) {
            Fabric.getLogger().d("CrashlyticsCore", "Removing invalid report file at " + file.getPath());
            file.delete();
        }
    }
}

