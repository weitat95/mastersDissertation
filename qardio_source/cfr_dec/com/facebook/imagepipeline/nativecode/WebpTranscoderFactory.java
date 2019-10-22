/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.nativecode;

import com.facebook.imagepipeline.nativecode.WebpTranscoder;

public class WebpTranscoderFactory {
    private static WebpTranscoder sWebpTranscoder;
    public static boolean sWebpTranscoderPresent;

    static {
        sWebpTranscoderPresent = false;
        try {
            sWebpTranscoder = (WebpTranscoder)Class.forName("com.facebook.imagepipeline.nativecode.WebpTranscoderImpl").newInstance();
            sWebpTranscoderPresent = true;
        }
        catch (Throwable throwable) {
            sWebpTranscoderPresent = false;
        }
    }

    public static WebpTranscoder getWebpTranscoder() {
        return sWebpTranscoder;
    }
}

