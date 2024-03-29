/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.bumptech.glide.load.model;

import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageVideoWrapperEncoder
implements Encoder<ImageVideoWrapper> {
    private final Encoder<ParcelFileDescriptor> fileDescriptorEncoder;
    private String id;
    private final Encoder<InputStream> streamEncoder;

    public ImageVideoWrapperEncoder(Encoder<InputStream> encoder, Encoder<ParcelFileDescriptor> encoder2) {
        this.streamEncoder = encoder;
        this.fileDescriptorEncoder = encoder2;
    }

    @Override
    public boolean encode(ImageVideoWrapper imageVideoWrapper, OutputStream outputStream) {
        if (imageVideoWrapper.getStream() != null) {
            return this.streamEncoder.encode(imageVideoWrapper.getStream(), outputStream);
        }
        return this.fileDescriptorEncoder.encode(imageVideoWrapper.getFileDescriptor(), outputStream);
    }

    @Override
    public String getId() {
        if (this.id == null) {
            this.id = this.streamEncoder.getId() + this.fileDescriptorEncoder.getId();
        }
        return this.id;
    }
}

