/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.resource.file;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.file.FileResource;
import java.io.File;
import java.io.IOException;

public class FileDecoder
implements ResourceDecoder<File, File> {
    @Override
    public Resource<File> decode(File file, int n, int n2) {
        return new FileResource(file);
    }

    @Override
    public String getId() {
        return "";
    }
}

