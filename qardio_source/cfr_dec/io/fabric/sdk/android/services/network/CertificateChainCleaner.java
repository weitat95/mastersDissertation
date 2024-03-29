/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.SystemKeyStore;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import javax.security.auth.x500.X500Principal;

final class CertificateChainCleaner {
    public static X509Certificate[] getCleanChain(X509Certificate[] object, SystemKeyStore systemKeyStore) throws CertificateException {
        boolean bl;
        LinkedList<X509Certificate> linkedList = new LinkedList<X509Certificate>();
        boolean bl2 = false;
        if (systemKeyStore.isTrustRoot(object[0])) {
            bl2 = true;
        }
        linkedList.add(object[0]);
        int n = 1;
        do {
            bl = bl2;
            if (n >= ((X509Certificate[])object).length) break;
            if (systemKeyStore.isTrustRoot(object[n])) {
                bl2 = true;
            }
            bl = bl2;
            if (!CertificateChainCleaner.isValidLink(object[n], object[n - 1])) break;
            linkedList.add(object[n]);
            ++n;
        } while (true);
        if ((object = systemKeyStore.getTrustRootFor(object[n - 1])) != null) {
            linkedList.add((X509Certificate)object);
            bl = true;
        }
        if (bl) {
            return linkedList.toArray(new X509Certificate[linkedList.size()]);
        }
        throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
    }

    private static boolean isValidLink(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (!x509Certificate.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
            return false;
        }
        try {
            x509Certificate2.verify(x509Certificate.getPublicKey());
            return true;
        }
        catch (GeneralSecurityException generalSecurityException) {
            return false;
        }
    }
}

