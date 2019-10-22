/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public interface Key {
    public void updateDiskCacheKey(MessageDigest var1) throws UnsupportedEncodingException;
}

