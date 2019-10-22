/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

class OriginalKey
implements Key {
    private final String id;
    private final Key signature;

    public OriginalKey(String string2, Key key) {
        this.id = string2;
        this.signature = key;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (this == object) break block5;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (OriginalKey)object;
                if (!this.id.equals(((OriginalKey)object).id)) {
                    return false;
                }
                if (!this.signature.equals(((OriginalKey)object).signature)) break block6;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode() * 31 + this.signature.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(this.id.getBytes("UTF-8"));
        this.signature.updateDiskCacheKey(messageDigest);
    }
}

