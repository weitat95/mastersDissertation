/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.CertificateChainCleaner;
import io.fabric.sdk.android.services.network.PinningInfoProvider;
import io.fabric.sdk.android.services.network.SystemKeyStore;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class PinningTrustManager
implements X509TrustManager {
    private static final X509Certificate[] NO_ISSUERS = new X509Certificate[0];
    private final Set<X509Certificate> cache;
    private final long pinCreationTimeMillis;
    private final List<byte[]> pins = new LinkedList<byte[]>();
    private final SystemKeyStore systemKeyStore;
    private final TrustManager[] systemTrustManagers;

    public PinningTrustManager(SystemKeyStore arrstring, PinningInfoProvider object2) {
        this.cache = Collections.synchronizedSet(new HashSet());
        this.systemTrustManagers = this.initializeSystemTrustManagers((SystemKeyStore)arrstring);
        this.systemKeyStore = arrstring;
        this.pinCreationTimeMillis = object2.getPinCreationTimeInMillis();
        for (String string2 : object2.getPins()) {
            this.pins.add(this.hexStringToByteArray(string2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkPinTrust(X509Certificate[] arrx509Certificate) throws CertificateException {
        if (this.pinCreationTimeMillis != -1L && System.currentTimeMillis() - this.pinCreationTimeMillis > 15552000000L) {
            Fabric.getLogger().w("Fabric", "Certificate pins are stale, (" + (System.currentTimeMillis() - this.pinCreationTimeMillis) + " millis vs " + 15552000000L + " millis) " + "falling back to system trust.");
            return;
        } else {
            arrx509Certificate = CertificateChainCleaner.getCleanChain(arrx509Certificate, this.systemKeyStore);
            int n = arrx509Certificate.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    throw new CertificateException("No valid pins found in chain!");
                }
                if (this.isValidPin(arrx509Certificate[n2])) return;
                ++n2;
            } while (true);
        }
    }

    private void checkSystemTrust(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        TrustManager[] arrtrustManager = this.systemTrustManagers;
        int n = arrtrustManager.length;
        for (int i = 0; i < n; ++i) {
            ((X509TrustManager)arrtrustManager[i]).checkServerTrusted(arrx509Certificate, string2);
        }
    }

    private byte[] hexStringToByteArray(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)((Character.digit(string2.charAt(i), 16) << 4) + Character.digit(string2.charAt(i + 1), 16));
        }
        return arrby;
    }

    private TrustManager[] initializeSystemTrustManagers(SystemKeyStore arrtrustManager) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(arrtrustManager.trustStore);
            arrtrustManager = trustManagerFactory.getTrustManagers();
            return arrtrustManager;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
        catch (KeyStoreException keyStoreException) {
            throw new AssertionError(keyStoreException);
        }
    }

    private boolean isValidPin(X509Certificate arrby) throws CertificateException {
        try {
            arrby = MessageDigest.getInstance("SHA1").digest(arrby.getPublicKey().getEncoded());
            Iterator<byte[]> iterator = this.pins.iterator();
            while (iterator.hasNext()) {
                boolean bl = Arrays.equals(iterator.next(), arrby);
                if (!bl) continue;
                return true;
            }
            return false;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new CertificateException(noSuchAlgorithmException);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        throw new CertificateException("Client certificates not supported!");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        if (this.cache.contains(arrx509Certificate[0])) {
            return;
        }
        this.checkSystemTrust(arrx509Certificate, string2);
        this.checkPinTrust(arrx509Certificate);
        this.cache.add(arrx509Certificate[0]);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return NO_ISSUERS;
    }
}

