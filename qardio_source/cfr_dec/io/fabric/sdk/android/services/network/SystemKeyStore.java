/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import javax.security.auth.x500.X500Principal;

class SystemKeyStore {
    private final HashMap<Principal, X509Certificate> trustRoots;
    final KeyStore trustStore;

    public SystemKeyStore(InputStream object, String string2) {
        object = this.getTrustStore((InputStream)object, string2);
        this.trustRoots = this.initializeTrustedRoots((KeyStore)object);
        this.trustStore = object;
    }

    private KeyStore getTrustStore(InputStream inputStream, String string2) {
        KeyStore keyStore = KeyStore.getInstance("BKS");
        inputStream = new BufferedInputStream(inputStream);
        try {
            keyStore.load(inputStream, string2.toCharArray());
        }
        catch (Throwable throwable) {
            try {
                ((BufferedInputStream)inputStream).close();
                throw throwable;
            }
            catch (KeyStoreException keyStoreException) {
                throw new AssertionError(keyStoreException);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new AssertionError(noSuchAlgorithmException);
            }
            catch (CertificateException certificateException) {
                throw new AssertionError(certificateException);
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }
        ((BufferedInputStream)inputStream).close();
        return keyStore;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore keyStore) {
        try {
            HashMap<Principal, X509Certificate> hashMap = new HashMap<Principal, X509Certificate>();
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                X509Certificate x509Certificate = (X509Certificate)keyStore.getCertificate(enumeration.nextElement());
                if (x509Certificate == null) continue;
                hashMap.put(x509Certificate.getSubjectX500Principal(), x509Certificate);
            }
            return hashMap;
        }
        catch (KeyStoreException keyStoreException) {
            throw new AssertionError(keyStoreException);
        }
    }

    public X509Certificate getTrustRootFor(X509Certificate x509Certificate) {
        X509Certificate x509Certificate2 = this.trustRoots.get(x509Certificate.getIssuerX500Principal());
        if (x509Certificate2 == null) {
            return null;
        }
        if (x509Certificate2.getSubjectX500Principal().equals(x509Certificate.getSubjectX500Principal())) {
            return null;
        }
        try {
            x509Certificate.verify(x509Certificate2.getPublicKey());
            return x509Certificate2;
        }
        catch (GeneralSecurityException generalSecurityException) {
            return null;
        }
    }

    public boolean isTrustRoot(X509Certificate x509Certificate) {
        X509Certificate x509Certificate2 = this.trustRoots.get(x509Certificate.getSubjectX500Principal());
        return x509Certificate2 != null && x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
    }
}

