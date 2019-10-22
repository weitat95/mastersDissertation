/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.facebook.common.util;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureHashUtil {
    static final byte[] HEX_CHAR_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public static String makeSHA1HashBase64(byte[] object) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update((byte[])object, 0, ((byte[])object).length);
            object = Base64.encodeToString((byte[])messageDigest.digest(), (int)11);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }
}

