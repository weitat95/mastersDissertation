/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.iid;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public final class zza {
    public static KeyPair zzavc() {
        try {
            Object object = KeyPairGenerator.getInstance("RSA");
            ((KeyPairGenerator)object).initialize(2048);
            object = ((KeyPairGenerator)object).generateKeyPair();
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }
}

